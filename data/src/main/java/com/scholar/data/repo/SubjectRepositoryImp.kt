package com.scholar.data.repo

import com.scholar.data.source.local.dao.SubjectDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.SubjectNetworkDataSource
import com.scholar.domain.repo.SubjectRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SubjectRepositoryImp(
    private val networkDataSource: SubjectNetworkDataSource,
    private val localDataSource: SubjectDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : SubjectRepository {
    override suspend fun getSubjects() = localDataSource.getSubjects()

    override suspend fun refresh() {
        val networkSubjects = networkDataSource.loadSubjects()
        if (networkSubjects.isEmpty()) return
        localDataSource.deleteAll()
        val localSubjects = withContext(dispatcher) {
            networkSubjects.toLocal()
        }
        localDataSource.upsertAll(localSubjects)
    }
}