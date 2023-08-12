package com.scholar.data.source.network

import com.scholar.data.service.ApiService
import com.scholar.domain.model.NetworkResult
import com.scholar.domain.model.Resource
import com.scholar.domain.model.SubjectNetwork
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class SubjectNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()


    suspend fun loadSubjects(): List<SubjectNetwork> = accessMutex.withLock {
        return when (val response = apiService.getSubjects()) {
            is NetworkResult.Success -> {
                response.data
            }
            else -> emptyList()
        }
    }
}