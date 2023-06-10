package com.scholar.domain.usecase

import com.scholar.domain.model.Category
import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.repo.CategoryRepository
import kotlinx.coroutines.flow.first

class CategoryUseCase(
    private val repository: CategoryRepository,
    private val networkConnectivity: NetworkConnectivity,
) {
    suspend operator fun invoke(): List<Category> {
        if (networkConnectivity.isNetworkAvailable()) {
            repository.refresh()
        }
        return repository.observeCategories().first()
    }
}