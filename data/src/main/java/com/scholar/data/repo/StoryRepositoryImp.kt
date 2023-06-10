package com.scholar.data.repo

import com.scholar.data.source.local.dao.StoryDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.StoryNetworkDataSource
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Story
import com.scholar.domain.repo.StoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StoryRepositoryImp(
    private val remoteDataSource: StoryNetworkDataSource,
    private val localDataSource: StoryDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : StoryRepository{

    override suspend fun getStoriesFromNetwork(): Resource<List<Story>> {
        val networkCategories = remoteDataSource.loadCategories()
        val localCategories = withContext(dispatcher) {
            networkCategories.toLocal()
        }
        localDataSource.upsertAll(localCategories)
        return Resource.Success(localCategories)
    }

    override suspend fun getStoriesFromLocal()= localDataSource.getStories()



}