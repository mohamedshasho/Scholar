package com.scholar.data.service

import com.scholar.domain.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {


    @GET("categories/list")
    suspend fun getCategories(): NetworkResult<List<CategoryNetwork>>

    @POST("teachers/list")
    @FormUrlEncoded
    suspend fun getTeachers(
        @Field("limit") limit: Int = PER_PAGE,
    ): NetworkResult<List<TeacherNetwork>>

    @POST("teachers/search")
    @FormUrlEncoded
    suspend fun searchForTeacher(
        @Field("teacher") teacher: String,
    ): NetworkResult<List<TeacherNetwork>>

    @POST("stories/list")
    @FormUrlEncoded
    suspend fun getStories(
        @Field("limit") limit: Int = PER_PAGE,
    ): NetworkResult<List<StoryNetwork>>

    @POST("stories/add")
    @FormUrlEncoded
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Field("title") title: String,
        @Field("description") description: String,
    ): NetworkResult<Boolean>

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