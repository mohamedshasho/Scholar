package com.scholar.data.source.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scholar.data.source.local.dao.TeacherDao
import com.scholar.domain.model.TeacherWithSubjects

class TeacherLocalPagingSource(
    private val teacherDao: TeacherDao,
) : PagingSource<Int, TeacherWithSubjects>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TeacherWithSubjects> {
        val currentPage = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val offset = (currentPage - 1) * pageSize
            val teachers = teacherDao.getTeachers(offset, pageSize)
            if(teachers.isNotEmpty()){
                LoadResult.Page(
                    data = teachers,
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

    override fun getRefreshKey(state: PagingState<Int, TeacherWithSubjects>) = state.anchorPosition

}