package com.scholar.data.source.network

import com.scholar.data.service.ApiService
import com.scholar.domain.model.StageNetwork
import javax.inject.Inject

class StageNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getStages() : List<StageNetwork>{
//        apiService.getStages()
        return emptyList()
    }

}