package com.scholar.domain.service

import com.scholar.domain.model.CategoryNetwork
import com.scholar.domain.model.NetworkResult
import retrofit2.http.GET

interface ApiService {
    @GET("getCategories")
    suspend fun getCategories(): NetworkResult<List<CategoryNetwork>>
}