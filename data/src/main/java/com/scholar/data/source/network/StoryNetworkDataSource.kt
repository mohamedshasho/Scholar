package com.scholar.data.source.network


import android.util.Log
import com.scholar.data.service.ApiService
import com.scholar.domain.model.NetworkResult
import com.scholar.domain.model.Resource
import com.scholar.domain.model.StoryNetwork
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


class StoryNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    suspend fun loadStories(page: Int): Resource<List<StoryNetwork>> = accessMutex.withLock {
        return when (val response = apiService.getStories(page)) {
            is NetworkResult.Success -> {
                Resource.Success(response.data)
            }
            is NetworkResult.Error -> {
                Resource.Error(response.error)
            }
            is NetworkResult.Exception -> {
                Resource.Error(response.e.message)
            }
        }
    }

    suspend fun addStory(
        filePath: String?,
        title: String,
        description: String,
        studentId: Int,
    ): Resource<Boolean> =
        accessMutex.withLock {

            if (filePath != null) {
                val fileRequestBody = filePath.let {
                    val file = File(it)
                    file.asRequestBody()
                }
                val filePart =
                    MultipartBody.Part.createFormData("storyImg", filePath, fileRequestBody)

                apiService.addStory(
                    file = filePart, title, studentId, description
                )
            } else {
                apiService.addStory(title, studentId, description)
            }

//                    map = map,

            return Resource.Success(true)
//            return when (val response =
//                apiService.addStory(
//                    file = filePart,
////                    map = map,
//                )) {
//                is NetworkResult.Success -> {
//                    Resource.Success(true)
//                }
//                is NetworkResult.Error -> {
//                    Resource.Error(response.error)
//                }
//                is NetworkResult.Exception -> {
//                    Resource.Error(response.e.message)
//                }
//            }
        }
}

