package com.scholar.data.source.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scholar.data.source.network.TeacherNetworkDataSource
import com.scholar.domain.model.Resource
import com.scholar.domain.model.TeacherWithSubjects
import com.scholar.domain.model.TeacherWithSubjectsNetwork

class TeachersSearchPagingSource(
    private val networkDataSource: TeacherNetworkDataSource,
    private val input: String,
) : PagingSource<Int, TeacherWithSubjects>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TeacherWithSubjects> {
        val currentPage = params.key ?: 1
        return when (val response = networkDataSource.search(currentPage,input)) {
            is Resource.Success -> {
                if (response.data.isNullOrEmpty()) {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                } else {
                    val teachersWithSubjects = response.data!!.map {
                        TeacherWithSubjectsNetwork(
                            teacher = it,
                            subjects = it.subjects
                        )
                    }
                    LoadResult.Page(
                        data = teachersWithSubjects,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = currentPage + 1
                    )
                }
            }
            is Resource.Error -> {
                LoadResult.Error(Throwable(response.message))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TeacherWithSubjects>) = state.anchorPosition
}