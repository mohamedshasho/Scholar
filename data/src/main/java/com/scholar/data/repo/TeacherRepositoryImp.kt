package com.scholar.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import androidx.paging.PagingData
import androidx.paging.ExperimentalPagingApi
import com.scholar.data.source.local.ScholarDb
import com.scholar.data.source.network.TeacherNetworkDataSource
import com.scholar.data.source.network.TeacherRemoteMediator
import com.scholar.data.source.network.paging.TeachersSearchPagingSource
import com.scholar.domain.model.MessageResponse
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Teacher
import com.scholar.domain.model.TeacherWithSubjects
import com.scholar.domain.repo.TeacherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class TeacherRepositoryImp(
    private val networkDataSource: TeacherNetworkDataSource,
    private val scholarDb: ScholarDb,
) : TeacherRepository {


    override suspend fun observeTeachers(): Flow<List<Teacher>> {
        return scholarDb.teacherDao().getTeachers()
    }
    override suspend fun getTeacherByIdFromLocal(id: Int) =
        scholarDb.teacherDao().getTeacherById(id)

    override fun searchForTeachers(input: String): Flow<PagingData<TeacherWithSubjects>> {
        return Pager(
            config = PagingConfig(pageSize = PAGER_SIZE),
            pagingSourceFactory = { TeachersSearchPagingSource(networkDataSource, input) },
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getTeachersPagination(): Flow<PagingData<TeacherWithSubjects>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGER_SIZE
            ),
            remoteMediator = TeacherRemoteMediator(
                teacherNetworkDataSource = networkDataSource,
                scholarDb = scholarDb,
            ),
            pagingSourceFactory = { scholarDb.teacherDao().getTeachersPaging() }
        ).flow.map { pagingData -> pagingData.map { it } }
    }


    override suspend fun contact(
        name: String,
        email: String,
        phone: String,
        subject: String
    ): Resource<MessageResponse> {
        return networkDataSource.contact(name, email, phone, subject)
    }
}

private const val PAGER_SIZE = 10