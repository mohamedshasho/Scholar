package com.scholar.data.repo

import com.scholar.data.source.local.dao.StageDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.StageNetworkDataSource
import com.scholar.domain.repo.StageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StageRepositoryImp(
    private val networkDataSource: StageNetworkDataSource,
    private val localDataSource: StageDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : StageRepository {
    override suspend fun refresh() {
        val networkTeachers = networkDataSource.getStages()
        localDataSource.deleteAll()
        val localTeachers = withContext(dispatcher) {
            networkTeachers.toLocal()
        }
        localDataSource.upsertAll(localTeachers)
    }

    override suspend fun observeStages() = localDataSource.observeStages()
}