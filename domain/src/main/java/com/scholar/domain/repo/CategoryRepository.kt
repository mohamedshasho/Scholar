package com.scholar.domain.repo

import com.scholar.domain.model.Category
import com.scholar.domain.model.Resource


interface CategoryRepository {
    suspend fun getCategoriesFromNetwork(): Resource<List<Category>>
    suspend fun getCategoriesFromLocal(): List<Category>
}