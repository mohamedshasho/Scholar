package com.scholar.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scholar.data.source.local.dao.MaterialDao
import com.scholar.data.source.local.dao.RateDao
import com.scholar.data.source.local.dao.TeacherDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.MaterialNetworkDataSource
import com.scholar.data.source.network.paging.MaterialsSearchPagingSource
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Material
import com.scholar.domain.model.MaterialWithDetail
import com.scholar.domain.model.MaterialWithTeacher
import com.scholar.domain.repo.MaterialRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MaterialRepositoryImp(
    private val remoteDataSource: MaterialNetworkDataSource,
    private val materialDao: MaterialDao,
    private val rateDao: RateDao,
    private val teacherDao: TeacherDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
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
}

private const val PAGER_SIZE=10