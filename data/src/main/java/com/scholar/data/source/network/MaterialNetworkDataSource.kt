package com.scholar.data.source.network

import com.scholar.data.service.ApiService
import com.scholar.domain.model.MaterialNetwork
import com.scholar.domain.model.NetworkResult
import com.scholar.domain.model.Resource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class MaterialNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    suspend fun loadSomeMaterials(): List<MaterialNetwork> = accessMutex.withLock {
        return when (val response = apiService.getSomeMaterials(limit = 5)) {
            is NetworkResult.Success -> {
                response.data
            }

            else -> emptyList()
        }
    }

    suspend fun search(page: Int, key: String): List<MaterialNetwork> = accessMutex.withLock {
        return when (val response = apiService.searchForMaterials(page = page, title = key)) {
            is NetworkResult.Success -> {
                response.data
            }

            else -> emptyList()
        }
    }

    suspend fun filter(
        page: Int,
        stageId: Int?,
        classroomId: Int?,
        subjectId: Int?,
        categoryId: Int?,
    ): List<MaterialNetwork> = accessMutex.withLock {
        return when (val response = apiService.filterMaterials(
            page = page,
            stage = stageId,
            classroom = classroomId,
            subject = subjectId,
            categoryId = categoryId,
        )) {
            is NetworkResult.Success -> {
                response.data
            }

            else -> emptyList()
        }
    }


    suspend fun getMaterial(id: Int): MaterialNetwork? = accessMutex.withLock {
        return when (val response = apiService.getMaterial(id)) {
            is NetworkResult.Success -> {
                response.data
            }

            else -> null
        }
    }

    suspend fun rate(
        studentId: Int,
        materialId: Int,
        rate: Int,
        comment: String
    ): Resource<String> {
        return when (val response = apiService.rateMaterial(studentId, materialId, rate, comment)) {
            is NetworkResult.Success -> {
                Resource.Success(response.data.message)
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
