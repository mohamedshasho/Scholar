package com.scholar.domain.repo

import com.scholar.domain.model.Category


interface CategoryRepository {
    suspend fun getCategoriesFromNetwork(): List<Category>
    suspend fun getCategoriesFromLocal(): List<Category>
}