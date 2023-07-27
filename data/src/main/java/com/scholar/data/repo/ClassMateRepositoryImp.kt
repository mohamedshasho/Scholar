package com.scholar.data.repo

import com.scholar.data.source.local.dao.ClassMateDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.ClassMateNetworkDataSource
import com.scholar.domain.model.ClassMate
import com.scholar.domain.repo.ClassMateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ClassMateRepositoryImp(
    private val networkDataSource: ClassMateNetworkDataSource,
    private val localDataSource: ClassMateDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ClassMateRepository {
    override suspend fun refresh() {
        val networkTeachers = networkDataSource.getClassesMate()
        localDataSource.deleteAll()
        val localTeachers = withContext(dispatcher) {
            networkTeachers.toLocal()
        }
        localDataSource.upsertAll(localTeachers)
    }

    override suspend fun observeClassesMate(id: Int) = localDataSource.observeClassesMate(id)
}