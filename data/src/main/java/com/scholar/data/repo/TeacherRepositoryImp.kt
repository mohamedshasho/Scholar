package com.scholar.data.repo

import com.scholar.data.source.local.dao.TeacherDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.TeacherNetworkDataSource
import com.scholar.domain.model.Teacher
import com.scholar.domain.repo.TeacherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class TeacherRepositoryImp(
    private val networkDataSource: TeacherNetworkDataSource,
    private val localDataSource: TeacherDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : TeacherRepository {


    override suspend fun observeTeachers(): Flow<List<Teacher>> {
        return localDataSource.getTeachers()
    }

    override suspend fun getTeacherByIdFromLocal(id: Int) = localDataSource.getTeacherById(id)

    override suspend fun refresh() {
        val networkTeachers = networkDataSource.loadTeachers()
        localDataSource.deleteAll()
        val localTeachers = withContext(dispatcher) {
            networkTeachers.toLocal()
        }
        localDataSource.upsertAll(localTeachers)
    }

}