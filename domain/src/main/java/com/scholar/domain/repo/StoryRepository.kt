package com.scholar.domain.repo

import com.scholar.domain.model.Resource
import com.scholar.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface StoryRepository {

    suspend fun  getStoriesFromNetwork(): Resource<List<Story>>
    suspend fun  getStoriesFromLocal(): Flow<List<Story>>
}