package com.scholar.data.repo

import com.scholar.data.source.local.dao.MaterialDao
import com.scholar.data.source.local.dao.RateDao
import com.scholar.data.source.local.dao.TeacherDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.MaterialNetworkDataSource
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Material
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

    override suspend fun getMaterialFromLocal(id: Int): Material {
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
        //    todo     localDataSource.upsertAll(localMaterials)
        return Resource.Success(localMaterials)
    }


    override suspend fun getMaterialFromNetwork(id: Int): Resource<Material> {
        val networkMaterial = remoteDataSource.getMaterial(id)
        return if (networkMaterial == null) Resource.Error(message = "empty")
        else {
            materialDao.upsert(networkMaterial.toLocal())
            rateDao.upsertAll(networkMaterial.rates.toLocal())
            return Resource.Success(networkMaterial)
        }
    }

}