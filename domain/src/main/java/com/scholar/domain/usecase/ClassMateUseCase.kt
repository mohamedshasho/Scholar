package com.scholar.domain.usecase

import com.scholar.domain.model.ClassMate
import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.repo.ClassMateRepository
import kotlinx.coroutines.flow.first

class ClassMateUseCase(
    private val repository: ClassMateRepository,
    private val networkConnectivity: NetworkConnectivity,
) {
    suspend operator fun invoke(id: Int): List<ClassMate> {
        if (networkConnectivity.isNetworkAvailable()) {
            repository.refresh()
        }
        return repository.observeClassesMate(id).first()
    }
}