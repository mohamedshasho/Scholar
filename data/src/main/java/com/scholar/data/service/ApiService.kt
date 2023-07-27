package com.scholar.data.service

import com.scholar.domain.model.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    @GET("categories/list")
    suspend fun getCategories(): NetworkResult<List<CategoryNetwork>>

    @POST("teachers/list")
    @FormUrlEncoded
    suspend fun getTeachers(
        @Field("limit") limit: Int = PER_PAGE,
    ): NetworkResult<List<TeacherNetwork>>

    @POST("stories/list")
    @FormUrlEncoded
    suspend fun getStories(
        @Field("limit") limit: Int = PER_PAGE,
    ): NetworkResult<List<StoryNetwork>>

    @GET("getAllMaterials")
    suspend fun getMaterials(): NetworkResult<List<MaterialNetwork>>


    @GET("class-room/list")
    suspend fun getClassRooms(): NetworkResult<List<ClassMateNetwork>>

    @GET("stages/list")
    suspend fun getStages(): NetworkResult<List<StageNetwork>>

    @POST("getSomeMaterials")
    @FormUrlEncoded
    suspend fun getSomeMaterials(
        @Field("limit") limit: Int,
    ): NetworkResult<List<MaterialNetwork>>
}

private const val PER_PAGE = 10