package com.scholar.data.source.network

import com.scholar.data.service.ApiService
import com.scholar.domain.model.ClassMateNetwork
import javax.inject.Inject

class ClassMateNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getClassesMate() : List<ClassMateNetwork>{
//        apiService.getClasses()
        return emptyList()
    }
}