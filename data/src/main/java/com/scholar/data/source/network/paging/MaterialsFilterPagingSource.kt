package com.scholar.data.source.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.scholar.data.source.network.MaterialNetworkDataSource
import com.scholar.domain.model.MaterialWithTeacher
import com.scholar.domain.model.MaterialWithTeacherNetwork
import com.scholar.domain.model.toSmall

class MaterialsFilterPagingSource(
    private val networkDataSource: MaterialNetworkDataSource,
    private val stageId: Int?,
    private val classroomId: Int?,
    private val subjectId: Int?,
    private val categoryId: Int?,
) : PagingSource<Int, MaterialWithTeacher>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MaterialWithTeacher> {
        val currentPage = params.key ?: 1

        val response = networkDataSource.filter(
            currentPage, stageId, classroomId, subjectId, categoryId
        )
        return if (response.isNotEmpty()) {
            val materialsWithTeacher = response.map {
                MaterialWithTeacherNetwork(
                    material = it,
                    teacher = it.teacher.toSmall(),
                    totalRate = it.rates.sumOf { rate -> rate.rate }.div(it.rates.size.toDouble())

                )
            }
            LoadResult.Page(
                data = materialsWithTeacher,
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
    }

    override fun getRefreshKey(state: PagingState<Int, MaterialWithTeacher>) = state.anchorPosition
}