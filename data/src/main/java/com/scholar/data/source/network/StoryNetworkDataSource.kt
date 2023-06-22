package com.scholar.data.source.network

import com.scholar.data.service.ApiService
import com.scholar.domain.model.StoryNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class StoryNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
){
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    private fun dummyData() =
        listOf(
            StoryNetwork(
                id = 1,
                title = "title 1",
                description = null,
                image = null,
            ),
            StoryNetwork(
                id = 1,
                title = "title 1",
                description = null,
                image = null,
            ),
        )

    suspend fun loadCategories(): List<StoryNetwork> = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        val response = apiService.getStories()
        return dummyData()
    }
}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L
