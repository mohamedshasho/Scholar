package com.scholar.domain.usecase

import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.model.Stage
import com.scholar.domain.repo.StageRepository
import kotlinx.coroutines.flow.first

class StageUseCase(
    private val repository: StageRepository,
    private val networkConnectivity: NetworkConnectivity,
) {
    suspend operator fun invoke(): List<Stage> {
        if (networkConnectivity.isNetworkAvailable()) {
            repository.refresh()
        }
        return repository.observeStages().first()
    }
}