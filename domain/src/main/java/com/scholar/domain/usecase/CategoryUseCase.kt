package com.scholar.domain.usecase

import com.scholar.domain.model.Category
import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.CategoryRepository

class CategoryUseCase(
    private val repository: CategoryRepository,
    private val networkConnectivity: NetworkConnectivity,
) {
    suspend operator fun invoke(): Resource<List<Category>> {
        return try {
            if (networkConnectivity.isNetworkAvailable()) {
                repository.getCategoriesFromNetwork()
            } else {
                Resource.Success(data = repository.getCategoriesFromLocal())
            }
        } catch (e: Exception) {
            Resource.Error(
                data = repository.getCategoriesFromLocal(), message = e.message
            )
        }
    }

}