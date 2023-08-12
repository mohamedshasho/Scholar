package com.scholar.data.service

import com.scholar.domain.model.*
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Field
import retrofit2.http.Multipart

interface ApiService {


    @GET("categories/list")
    suspend fun getCategories(): NetworkResult<List<CategoryNetwork>>

    @GET("teacher")
    suspend fun getTeachers(@Query("page") page: Int): NetworkResult<List<TeacherNetwork>>

    @POST("teachers/search")
    @FormUrlEncoded
    suspend fun searchForTeacher(
        @Field("teacher") teacher: String,
    ): NetworkResult<List<TeacherNetwork>>

    @GET("student/stories")
    suspend fun getStories(@Query("page") page: Int): NetworkResult<List<StoryNetwork>>

    @Multipart
    @POST("student/add-story")
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Part("title") title: String,
        @Part("studentId") studentId: Int,
        @Part("description") description: String,
    )

    @FormUrlEncoded
    @POST("student/add-story")
    suspend fun addStory(
        @Field("title") title: String,
        @Field("studentId") studentId: Int,
        @Field("description") description: String,
    )

    @GET("getAllMaterials")
    suspend fun getMaterials(): NetworkResult<List<MaterialNetwork>>

    @GET("subjects")
    suspend fun getSubjects(): NetworkResult<List<SubjectNetwork>>

    @GET("class-room/list")
    suspend fun getClassRooms(): NetworkResult<List<ClassRoomNetwork>>

    @GET("stages/list")
    suspend fun getStages(): NetworkResult<List<StageNetwork>>

    @POST("material/get-random")
    @FormUrlEncoded
    suspend fun getSomeMaterials(
        @Field("limit") limit: Int,
    ): NetworkResult<List<MaterialNetwork>>


    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): NetworkResult<StudentNetwork>

    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("full_name") fullName: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): NetworkResult<StudentNetwork>

    @POST("logout")
    suspend fun logout(): NetworkResult<Boolean>

}

private const val PER_PAGE = 10