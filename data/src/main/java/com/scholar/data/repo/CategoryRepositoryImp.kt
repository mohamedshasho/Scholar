package com.scholar.data.repo

import com.scholar.data.source.local.dao.CategoryDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.CategoryNetworkDataSource
import com.scholar.domain.model.Category
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CategoryRepositoryImp(
    private val remoteDataSource: CategoryNetworkDataSource,
    private val localDataSource: CategoryDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : CategoryRepository {
    override suspend fun observeCategories(): Flow<List<Category>> {
        return localDataSource.getCategories()
    }

    override suspend fun refresh() {
        when (val response = remoteDataSource.loadCategories()) {
            is Resource.Success -> {
                response.data?.let { networkTeachers ->
                    val localTeachers = withContext(dispatcher) {
                        networkTeachers.toLocal()
                    }
                    localDataSource.upsertAll(localTeachers)
                }
            }
            else -> {}
        }
    }

}