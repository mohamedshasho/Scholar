package com.scholar.data.source.network.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.scholar.data.source.local.ScholarDb
import com.scholar.data.source.local.model.StoryRemoteKeys
import com.scholar.data.source.local.model.StoryWithStudentLocal
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.StoryNetworkDataSource
import com.scholar.domain.model.Resource
import com.scholar.domain.model.StoryWithStudent

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val remoteDataSource: StoryNetworkDataSource,
    private val scholarDb: ScholarDb,
) : RemoteMediator<Int, StoryWithStudentLocal>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private val storyDao = scholarDb.storyDao()
    private val studentDao = scholarDb.studentDao()
    private val storyRemoteKeysDao = scholarDb.storyRemoteKeysDao()


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoryWithStudentLocal>,
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
        return when (val response = remoteDataSource.loadStories(currentPage)) {
            is Resource.Success -> {
                val endOfPaginationReached = response.data?.isEmpty() ?: true
                val prevPage = if (currentPage == 1) null else currentPage - 1
                val nextPage = if (endOfPaginationReached) null else currentPage + 1
                scholarDb.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        storyDao.deleteAll()
                        storyRemoteKeysDao.deleteAll()
                    }
                    val storiesNetwork = response.data
                    val keys = storiesNetwork?.map { storyNetwork ->
                        StoryRemoteKeys(
                            id = storyNetwork.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    storiesNetwork?.let { stories ->

                        stories.forEach {
                            storyDao.upsert(it.toLocal())
                            studentDao.upsert(it.student.toLocal())
                        }
                    }
                    keys?.let { storyRemoteKeysDao.upsertAll(it) }

                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
            is Resource.Error -> {
                MediatorResult.Error(Throwable(response.message))
            }
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, StoryWithStudentLocal>,
    ): StoryRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.story?.id?.let { id ->
                storyRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, StoryWithStudentLocal>,
    ): StoryRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { story ->
                storyRemoteKeysDao.getRemoteKeys(id = story.story.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, StoryWithStudentLocal>): StoryRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                storyRemoteKeysDao.getRemoteKeys(repo.story.id)
            }
    }
}