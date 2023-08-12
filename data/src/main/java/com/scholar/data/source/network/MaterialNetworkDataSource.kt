package com.scholar.data.source.network

import com.scholar.data.service.ApiService
import com.scholar.domain.model.MaterialNetwork
import com.scholar.domain.model.NetworkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class MaterialNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    suspend fun loadMaterials(): List<MaterialNetwork> = accessMutex.withLock {
//        val response = apiService.getMaterials()
        return emptyList()
    }

    suspend fun loadSomeMaterials(): List<MaterialNetwork> = accessMutex.withLock {
        return when (val response = apiService.getSomeMaterials(limit = 5)) {
            is NetworkResult.Success -> {
                response.data
            }
            else -> emptyList()
        }
    }
}
