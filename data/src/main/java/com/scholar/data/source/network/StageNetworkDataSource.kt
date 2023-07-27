package com.scholar.data.source.network

import com.scholar.data.service.ApiService
import com.scholar.domain.model.NetworkResult
import com.scholar.domain.model.StageNetwork
import javax.inject.Inject

class StageNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getStages(): List<StageNetwork> {
        return when (val response = apiService.getStages()) {
            is NetworkResult.Success -> {
                response.data
            }
            else -> {
                emptyList()
            }
        }
    }

}