package com.scholar.data.source.network

import com.scholar.data.service.ApiService
import com.scholar.domain.model.MessageResponse
import com.scholar.domain.model.NetworkResult
import com.scholar.domain.model.Resource
import com.scholar.domain.model.SubjectNetwork
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class StudentNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()


    suspend fun purchaseCredit(
        filePath: String,
        studentId: Int,
        amount: Int,
        paymentMethod: Int,
        description: String?
    ): Resource<MessageResponse> = accessMutex.withLock {

        val fileRequestBody = filePath.let {
            val file = File(it)
            file.asRequestBody()
        }
        val filePart =
            MultipartBody.Part.createFormData("link", filePath, fileRequestBody)

        return when (val response =
            apiService.purchaseCredit(filePart, studentId, amount, paymentMethod, description)) {
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

    suspend fun purchaseMaterial(
      studentId: Int,
      materialId: Int,
    ): Resource<MessageResponse> = accessMutex.withLock {
        return when (val response =
            apiService.purchaseMaterial(studentId, materialId)) {
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

}