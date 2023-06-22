package com.scholar.data.service

import com.scholar.domain.model.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("getCategories")
    suspend fun getCategories(): NetworkResult<List<CategoryNetwork>>

    @GET("getAllTeachers")
    suspend fun getTeachers(): NetworkResult<List<TeacherNetwork>>

    @GET("getAllStory")
    suspend fun getStories(): NetworkResult<List<StoryNetwork>>

    @GET("getAllMaterials")
    suspend fun getMaterials(): NetworkResult<List<MaterialNetwork>>

    @POST("getSomeMaterials")
    @FormUrlEncoded
    suspend fun getSomeMaterials(
        @Field("limit") limit: Int,
    ): NetworkResult<List<MaterialNetwork>>
}