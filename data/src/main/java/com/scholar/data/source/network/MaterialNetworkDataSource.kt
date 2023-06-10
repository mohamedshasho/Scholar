package com.scholar.data.source.network

import com.scholar.data.source.network.service.ApiService
import com.scholar.domain.model.MaterialNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class MaterialNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    private fun dummyData() =
        listOf(
            MaterialNetwork(
                id = 1,
                title = "Material 1",
                description = "description 1",
                price = 5.2,
                content = null
            ),
            MaterialNetwork(
                id = 2,
                title = "Material 2",
                description = "description 2",
                price = 5.2,
                content = null
            ),
            MaterialNetwork(
                id = 3,
                title = "Material 3",
                description = "description 3",
                price = 5.2,
                content = null
            ),
            MaterialNetwork(
                id = 4,
                title = "Material 4",
                description = "description 4",
                price = 5.2,
                content = null
            ),
        )

    suspend fun loadMaterials(): List<MaterialNetwork> = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        val response = apiService.getMaterials()
        return dummyData()
    }

    suspend fun loadSomeMaterials(): List<MaterialNetwork> = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        val response = apiService.getSomeMaterials(limit = 4)
        return dummyData()
    }
}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L
