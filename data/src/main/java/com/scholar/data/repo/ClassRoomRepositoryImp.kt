package com.scholar.data.repo

import com.scholar.data.source.local.dao.ClassRoomDao
import com.scholar.data.source.network.ClassMateNetworkDataSource
import com.scholar.domain.repo.ClassRoomRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ClassRoomRepositoryImp(
    private val networkDataSource: ClassMateNetworkDataSource,
    private val localDataSource: ClassRoomDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ClassRoomRepository {
    override suspend fun refresh() {
        /*
        val networkTeachers = networkDataSource.getClassesMate()
        localDataSource.deleteAll()
        val localTeachers = withContext(dispatcher) {
            networkTeachers.toLocal()
        }
        localDataSource.upsertAll(localTeachers)

         */
    }

    override suspend fun observeClassesRooms() = localDataSource.observeClassesMate()
}