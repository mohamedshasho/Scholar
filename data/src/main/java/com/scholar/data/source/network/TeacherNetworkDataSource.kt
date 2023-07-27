package com.scholar.data.source.network

import com.scholar.data.service.ApiService
import com.scholar.domain.model.NetworkResult
import com.scholar.domain.model.Resource
import com.scholar.domain.model.TeacherNetwork
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class TeacherNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
){
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    suspend fun loadTeachers(): Resource<List<TeacherNetwork>> = accessMutex.withLock {
        return when (val response = apiService.getTeachers()) {
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