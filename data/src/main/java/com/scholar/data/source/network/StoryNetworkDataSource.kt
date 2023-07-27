package com.scholar.data.source.network


import com.scholar.data.service.ApiService
import com.scholar.domain.model.NetworkResult
import com.scholar.domain.model.Resource
import com.scholar.domain.model.StoryNetwork
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class StoryNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    suspend fun loadStories(): Resource<List<StoryNetwork>> = accessMutex.withLock {
        return when (val response = apiService.getStories()) {
            is NetworkResult.Success -> {
                Resource.Success(response.data)
            }
            is NetworkResult.Error -> {
                Resource.Error(response.error)
            }
            is NetworkResult.Exception -> {
                Resource.Error(response.e.message)
            }
        }
    }
}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L
