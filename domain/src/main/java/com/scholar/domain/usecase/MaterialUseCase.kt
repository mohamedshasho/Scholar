package com.scholar.domain.usecase

import com.scholar.domain.model.MaterialWithDetail
import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.repo.MaterialRepository
import kotlinx.coroutines.flow.first

class MaterialUseCase(
    private val repository: MaterialRepository,
    private val networkConnectivity: NetworkConnectivity,
) {
    suspend operator fun invoke(id: Int): MaterialWithDetail {
        if (networkConnectivity.isNetworkAvailable()) {
              repository.getMaterialFromNetwork(id)
        }

        return repository.getMaterialFromLocal(id).first()
    }
}