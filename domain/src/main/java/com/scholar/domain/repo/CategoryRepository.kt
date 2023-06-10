package com.scholar.domain.repo

import com.scholar.domain.model.Category
import com.scholar.domain.model.Resource
import kotlinx.coroutines.flow.Flow


interface CategoryRepository {
    suspend fun refresh()
    suspend fun observeCategories(): Flow<List<Category>>
}