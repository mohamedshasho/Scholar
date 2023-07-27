package com.scholar.data.source.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scholar.data.source.local.dao.StoryDao
import com.scholar.domain.model.Story

class StoryLocalPagingSource(
    private val storyDao: StoryDao,
) : PagingSource<Int, Story>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        val currentPage = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val offset = (currentPage - 1) * pageSize
            val stories = storyDao.getStories(offset, pageSize)
            if(stories.isNotEmpty()){
                LoadResult.Page(
                    data = stories,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Story>) = state.anchorPosition

}