package com.scholar.data.remote

import retrofit2.http.POST


interface ApiService {

    @POST()
    suspend fun getData()
}