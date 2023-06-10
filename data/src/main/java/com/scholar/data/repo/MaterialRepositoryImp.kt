package com.scholar.data.repo

import com.scholar.data.source.local.dao.MaterialDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.MaterialNetworkDataSource
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Material
import com.scholar.domain.repo.MaterialRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MaterialRepositoryImp(
    private val remoteDataSource: MaterialNetworkDataSource,
    private val localDataSource: MaterialDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : MaterialRepository {


    override suspend fun observeMaterials(): Flow<List<Material>> {
        return localDataSource.getMaterials()
    }

    override suspend fun getSomeMaterials(): Resource<List<Material>> {
        val networkMaterials = remoteDataSource.loadSomeMaterials()
        val localMaterials = withContext(dispatcher) {
            networkMaterials.toLocal()
        }
        localDataSource.upsertAll(localMaterials)
        return Resource.Success(localMaterials)
    }

    override suspend fun getMaterialsFromNetwork(): Resource<List<Material>> {
        val networkMaterials = remoteDataSource.loadMaterials()
        val localMaterials = withContext(dispatcher) {
            networkMaterials.toLocal()
        }
        localDataSource.upsertAll(localMaterials)
        return Resource.Success(localMaterials)
    }

    override suspend fun getSomeMaterialsFromNetwork(): Resource<List<Material>> {
        val networkMaterials = remoteDataSource.loadMaterials()
        val localMaterials = withContext(dispatcher) {
            networkMaterials.toLocal()
        }
        localDataSource.upsertAll(localMaterials)
        return Resource.Success(localMaterials)
    }


}