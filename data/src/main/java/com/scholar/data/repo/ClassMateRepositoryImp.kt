package com.scholar.data.repo

import com.scholar.data.source.local.dao.ClassMateDao
import com.scholar.data.source.network.ClassMateNetworkDataSource
import com.scholar.domain.model.ClassMate
import com.scholar.domain.repo.ClassMateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ClassMateRepositoryImp(
    private val remoteDataSource: ClassMateNetworkDataSource,
    private val localDataSource: ClassMateDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ClassMateRepository {
    override suspend fun refresh() {
        TODO("Not yet implemented")
    }

    override suspend fun observeClassesMate(): Flow<List<ClassMate>> {
        TODO("Not yet implemented")
    }
}