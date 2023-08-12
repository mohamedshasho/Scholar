package com.scholar.domain.repo

import androidx.paging.PagingData
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Story
import com.scholar.domain.model.StoryWithStudent
import kotlinx.coroutines.flow.Flow
import java.io.File

interface StoryRepository {
    fun getStoriesFromLocal(): Flow<List<Story>>
    fun getStoryFromLocal(id: Int): Flow<Story>
    fun getStoriesPagination(): Flow<PagingData<StoryWithStudent>>
    suspend fun addStoryToNetwork(
        filePath: String?,
        title: String,
        description: String,
        studentId: Int,
    ): Resource<Boolean>
}