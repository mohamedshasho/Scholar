package com.scholar.domain.repo

import androidx.paging.PagingData
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getStoriesFromLocal(): Flow<List<Story>>
    fun getStoryFromLocal(id: Int): Flow<Story>
    fun getStoriesPagination(): Flow<PagingData<Story>>
}