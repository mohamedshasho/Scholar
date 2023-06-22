package com.scholar.data.repo

import com.scholar.data.source.local.dao.StageDao
import com.scholar.data.source.network.StageNetworkDataSource
import com.scholar.domain.model.Stage
import com.scholar.domain.repo.StageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class StageRepositoryImp(
    private val remoteDataSource: StageNetworkDataSource,
    private val localDataSource: StageDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : StageRepository {
    override suspend fun refresh() {
        TODO("Not yet implemented")
    }

    override suspend fun observeStages(): Flow<List<Stage>> {
        TODO("Not yet implemented")
    }
}