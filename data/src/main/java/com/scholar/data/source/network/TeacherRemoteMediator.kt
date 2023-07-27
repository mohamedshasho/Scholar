package com.scholar.data.source.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.scholar.data.source.local.ScholarDb
import com.scholar.data.source.local.model.TeacherRemoteKeys
import com.scholar.data.source.local.model.TeacherSubjectCrossRef
import com.scholar.data.source.local.model.toLocal
import com.scholar.domain.model.Resource
import com.scholar.domain.model.TeacherWithSubjects


@OptIn(ExperimentalPagingApi::class)
class TeacherRemoteMediator(
    private val teacherNetworkDataSource: TeacherNetworkDataSource,
    private val scholarDb: ScholarDb,
) : RemoteMediator<Int, TeacherWithSubjects>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private val teacherDap = scholarDb.teacherDao()
    private val teacherRemoteKeysDao = scholarDb.teacherRemoteKeysDao()


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TeacherWithSubjects>,
    ): MediatorResult {
        val currentPage = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKeys?.prevPage
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                prevPage
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage = remoteKeys?.nextPage
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                nextPage
            }
        }
        return when (val response = teacherNetworkDataSource.loadTeachers()) {
            is Resource.Success -> {
                val endOfPaginationReached = response.data?.isEmpty() ?: true
                val prevPage = if (currentPage == 1) null else currentPage - 1
                val nextPage = if (endOfPaginationReached) null else currentPage + 1
                scholarDb.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        teacherDap.deleteAll()
                        teacherRemoteKeysDao.deleteAll()
                    }
                    val teachersNetwork = response.data
                    val keys = teachersNetwork?.map { teacherNetwork ->
                        teacherDap.upsert(teacherNetwork.toLocal())
                        teacherNetwork.subjects.forEach { subject->
                            scholarDb.subjectDao().upsert(subject.toLocal())
                            scholarDb.subjectDao().insertTeacherSubject(
                                TeacherSubjectCrossRef(
                                    teacherId = teacherNetwork.id,
                                    subjectId =subject.id ,
                                )
                            )
                        }



                        TeacherRemoteKeys(
                            id = teacherNetwork.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }

                    teachersNetwork?.let { teacherDap.upsertAll(it.toLocal()) }
                    keys?.let { teacherRemoteKeysDao.upsertAll(it) }

                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
            is Resource.Error -> {
                MediatorResult.Error(Throwable(response.message))
            }
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TeacherWithSubjects>,
    ): TeacherRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.teacher?.id?.let { id ->
                teacherRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, TeacherWithSubjects>,
    ): TeacherRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                teacherRemoteKeysDao.getRemoteKeys(id = repo.teacher.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TeacherWithSubjects>): TeacherRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                teacherRemoteKeysDao.getRemoteKeys(repo.teacher.id)
            }
    }
}