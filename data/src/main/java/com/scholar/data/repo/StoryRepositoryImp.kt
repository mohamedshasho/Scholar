package com.scholar.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scholar.data.source.local.ScholarDb
import com.scholar.data.source.local.paging.StoryLocalPagingSource
import com.scholar.data.source.network.StoryNetworkDataSource
import com.scholar.data.source.network.StoryRemoteMediator
import com.scholar.domain.model.Story
import com.scholar.domain.repo.StoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class StoryRepositoryImp(
    private val remoteDataSource: StoryNetworkDataSource,
    private val scholarDb: ScholarDb,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : StoryRepository {

    override fun getStoriesFromLocal() = scholarDb.storyDao().getStories()


    @OptIn(ExperimentalPagingApi::class)
    override fun getStoriesPagination(): Flow<PagingData<Story>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGER_SIZE
            ),
            remoteMediator = StoryRemoteMediator(
                remoteDataSource = remoteDataSource,
                scholarDb = scholarDb,
            ),
            pagingSourceFactory = { StoryLocalPagingSource(scholarDb.storyDao()) }
        ).flow
    }

    override fun getStoryFromLocal(id: Int) = scholarDb.storyDao().getStory(id)
}

private const val PAGER_SIZE = 10