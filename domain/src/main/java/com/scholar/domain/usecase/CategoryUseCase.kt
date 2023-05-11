package com.scholar.domain.usecase

import com.scholar.domain.model.Category
import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.repo.CategoryRepository

class CategoryUseCase(
    private val repository: CategoryRepository,
    private val networkConnectivity: NetworkConnectivity,
) {
    suspend operator fun invoke(): List<Category> {
        return try {
            if (networkConnectivity.isNetworkAvailable()) {
                repository.getCategoriesFromNetwork()
            } else {
                repository.getCategoriesFromLocal()
            }
        } catch (e: Exception) {
            repository.getCategoriesFromLocal()
        }
    }

}