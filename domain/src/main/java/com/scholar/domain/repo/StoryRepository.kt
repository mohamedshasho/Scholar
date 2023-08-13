package com.scholar.domain.repo

import androidx.paging.PagingData
import com.scholar.domain.model.Resource
import com.scholar.domain.model.StoryWithStudent
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getStoriesFromLocal(): Flow<List<StoryWithStudent>>
    fun getStoryFromLocal(id: Int): Flow<StoryWithStudent>
    fun getStoriesPagination(): Flow<PagingData<StoryWithStudent>>
    suspend fun addStoryToNetwork(
        filePath: String?,
        title: String,
        description: String,
        studentId: Int,
    ): Resource<String>
}