package com.scholar.data.repo

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.scholar.data.source.local.dao.MaterialDao
import com.scholar.data.source.local.dao.RateDao
import com.scholar.data.source.local.dao.TeacherDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.MaterialNetworkDataSource
import com.scholar.data.source.network.paging.MaterialsFilterPagingSource
import com.scholar.data.source.network.paging.MaterialsSearchPagingSource
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Material
import com.scholar.domain.model.MaterialWithDetail
import com.scholar.domain.model.MaterialWithTeacher
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.MaterialRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class MaterialRepositoryImp(
    private val dataStorePreference: DataStorePreference,
    private val remoteDataSource: MaterialNetworkDataSource,
    private val materialDao: MaterialDao,
    private val rateDao: RateDao,
    private val teacherDao: TeacherDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val context: Context,
) : MaterialRepository {


    override suspend fun observeMaterials(): Flow<List<Material>> {
        return materialDao.getMaterials()
    }

    override suspend fun getSomeMaterials(): Flow<List<MaterialWithTeacher>> {
        val networkMaterials = remoteDataSource.loadSomeMaterials()
        networkMaterials.forEach { material ->
            materialDao.upsert(material.toLocal())
            material.teacher?.let { teacherDao.upsert(it.toLocal()) }
            rateDao.upsertAll(material.rates.toLocal())
        }

        return materialDao.getSomeMaterials(limit = 5)
    }

    override suspend fun getMaterialFromLocal(id: Int): Flow<MaterialWithDetail> {
        return materialDao.getMaterial(id)
    }

    override suspend fun getMaterialsFromNetwork(): Resource<List<Material>> {
        val networkMaterials = remoteDataSource.loadMaterials()
        val localMaterials = withContext(dispatcher) {
            networkMaterials.toLocal()
        }
//    todo    localDataSource.upsertAll(localMaterials)
        return Resource.Success(localMaterials)
    }

    override suspend fun getSomeMaterialsFromNetwork(): Resource<List<Material>> {
        val networkMaterials = remoteDataSource.loadMaterials()
        val localMaterials = withContext(dispatcher) {
            networkMaterials.toLocal()
        }
        return Resource.Success(localMaterials)
    }


    override suspend fun getMaterialFromNetwork(id: Int) {
        val networkMaterial = remoteDataSource.getMaterial(id)
        if (networkMaterial != null) {
            materialDao.upsert(networkMaterial.toLocal())
            rateDao.upsertAll(networkMaterial.rates.toLocal())
        }
    }

    override suspend fun searchForMaterialFromNetwork(key: String): Flow<PagingData<MaterialWithTeacher>> {
        return Pager(
            config = PagingConfig(pageSize = PAGER_SIZE),
            pagingSourceFactory = { MaterialsSearchPagingSource(remoteDataSource, key) },
        ).flow
    }


    override suspend fun filterMaterialFromNetwork(
        stageId: Int?,
        classroomId: Int?,
        subjectId: Int?,
        categoryId: Int?
    ): Flow<PagingData<MaterialWithTeacher>> {
        val m = dataStorePreference.readValue(DataStorePreference.myMaterials).first()
        val myMaterial = Gson().fromJson(m,Array<Int>::class.java)
        return Pager(
            config = PagingConfig(pageSize = PAGER_SIZE),
            pagingSourceFactory = {
                MaterialsFilterPagingSource(
                    myMaterial.toList(),
                    remoteDataSource,
                    stageId,
                    classroomId,
                    subjectId,
                    categoryId
                )
            },
        ).flow
    }


    override suspend fun loadPDf(url: URL, pdfBytes: (File?) -> Unit) {
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle error
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.byteStream()

                // Create a File instance to represent the destination file
                val destinationFile = File(context.getExternalFilesDir(null), "output.pdf")
                val byteArray = body?.readBytes()
                try {
                    // Create a FileOutputStream to write the byteArray to the file
                    val fileOutputStream = FileOutputStream(destinationFile)
                    fileOutputStream.write(byteArray)
                    fileOutputStream.close()

                    // Now the byteArray is saved to the file
                } catch (e: IOException) {
                    e.printStackTrace()
                    // Handle the exception, e.g., show an error message to the user
                }


                pdfBytes(destinationFile)
            }
        })
    }

    override suspend fun rateMaterial(
        studentId: Int,
        materialId: Int,
        rate: Int,
        comment: String
    ): Resource<String> {
        return  remoteDataSource.rate(studentId,materialId,rate,comment)
    }

}

private const val PAGER_SIZE = 10