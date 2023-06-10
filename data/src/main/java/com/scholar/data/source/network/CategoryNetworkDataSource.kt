package com.scholar.data.source.network

import com.scholar.data.source.network.service.ApiService
import com.scholar.domain.model.CategoryNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class CategoryNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
){
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    private fun dummyData() =
        listOf(
            CategoryNetwork(id = 1, name = "Category 1", null),
            CategoryNetwork(id = 2, name = "Category 2", null),
            CategoryNetwork(id = 3, name = "Category 3", null),
            CategoryNetwork(id = 4, name = "Category 4", null),
        )

    suspend fun loadCategories(): List<CategoryNetwork> = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        val response = apiService.getCategories()
        return dummyData()
    }
}
private const val SERVICE_LATENCY_IN_MILLIS = 2000L
