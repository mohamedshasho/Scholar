package com.scholar.data.source.network


import com.scholar.data.service.ApiService
import com.scholar.domain.model.NetworkResult
import com.scholar.domain.model.Resource
import com.scholar.domain.model.StoryNetwork
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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
    ): Resource<String> =
        accessMutex.withLock {
            return if (filePath != null) {
                val fileRequestBody = filePath.let {
                    val file = File(it)
                    file.asRequestBody()
                }
                val filePart =
                    MultipartBody.Part.createFormData("storyImg", filePath, fileRequestBody)
                when (val resource =
                    apiService.addStory(file = filePart, title, studentId, description)) {
                    is NetworkResult.Success -> {
                        Resource.Success(resource.data.message)
                    }
                    else -> {
                        Resource.Error("Error while added")
                    }
                }
            } else {
                when (val resource = apiService.addStory(title, studentId, description)) {
                    is NetworkResult.Success -> {
                        Resource.Success(resource.data.message)
                    }
                    else -> {
                        Resource.Error("Error while added")
                    }
                }
            }
        }
}

