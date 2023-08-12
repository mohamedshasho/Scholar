package com.scholar.data.source.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scholar.data.source.local.dao.StoryDao
import com.scholar.domain.model.StoryWithStudent

class StoryWithStudentPagingSource(
    private val storyDao: StoryDao,
) : PagingSource<Int, StoryWithStudent>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryWithStudent> {
        return when (val loadResult = storyDao.getStoriesWithStudent().load(params)) {
            is LoadResult.Error -> LoadResult.Error(loadResult.throwable)
            is LoadResult.Page -> {
                LoadResult.Page(
                    data = loadResult.data,
                    prevKey = loadResult.prevKey,
                    nextKey = loadResult.nextKey
                )
            }
            else -> LoadResult.Error(IllegalStateException("Invalid load result type"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StoryWithStudent>) = state.anchorPosition

}