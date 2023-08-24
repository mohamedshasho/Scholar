package com.scholar.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingConfig
import com.scholar.data.source.local.ScholarDb
import com.scholar.data.source.network.StoryNetworkDataSource
import com.scholar.data.source.network.paging.StoryRemoteMediator
import com.scholar.domain.model.Resource
import com.scholar.domain.model.StoryWithStudent
import com.scholar.domain.repo.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoryRepositoryImp(
    private val remoteDataSource: StoryNetworkDataSource,
    private val scholarDb: ScholarDb,
) : StoryRepository {

    override fun getStoriesFromLocal() = scholarDb.storyDao().getStories()

    override suspend fun addStoryToNetwork(
        filePath: String?,
        title: String,
        description: String,
        studentId: Int,
    ): Resource<String> {
        return remoteDataSource.addStory(filePath, title, description, studentId)
    }


    @OptIn(ExperimentalPagingApi::class)
    override fun getStoriesPagination(): Flow<PagingData<StoryWithStudent>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGER_SIZE
            ),
            remoteMediator = StoryRemoteMediator(
                remoteDataSource = remoteDataSource,
                scholarDb = scholarDb,
            ),
            pagingSourceFactory = { scholarDb.storyDao().getStoriesWithStudent() }
        ).flow.map { pagingData -> pagingData.map { it } }
    }

    override fun getStoryFromLocal(id: Int) = scholarDb.storyDao().getStory(id)
}

private const val PAGER_SIZE = 10