package com.scholar.data.repo

import com.scholar.data.service.ApiService
import com.scholar.data.source.local.dao.StudentDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.domain.model.NetworkResult
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Student
import com.scholar.domain.repo.AuthRepository

class AuthRepositoryImp(
    private val apiService: ApiService,
    private val studentDao: StudentDao,
) : AuthRepository {
    override suspend fun login(email: String, password: String): Resource<Int> {
        return when (val response = apiService.login(email, password)) {
            is NetworkResult.Success -> {
                val student = response.data
                studentDao.upsert(student.toLocal())
                Resource.Success(student.id)
            }
            is NetworkResult.Error -> {
                Resource.Error(response.error)
            }
            is NetworkResult.Exception -> {
                Resource.Error(response.e.message)
            }
        }
    }

    override suspend fun register(
        fullName: String,
        email: String,
        password: String,
    ): Resource<Int> {
        return when (val response = apiService.register(fullName, email, password)) {
            is NetworkResult.Success -> {
                val student = response.data
                studentDao.upsert(student.toLocal())
                Resource.Success(student.id)
            }
            is NetworkResult.Error -> {
                Resource.Error(response.error)
            }
            is NetworkResult.Exception -> {
                Resource.Error(response.e.message)
            }
        }
    }

    override suspend fun logout(): Resource<Boolean> {
        return when (val response = apiService.logout()) {
            is NetworkResult.Success -> {
                Resource.Success(true)
            }
            is NetworkResult.Error -> {
                Resource.Success(false)
            }
            is NetworkResult.Exception -> {
                Resource.Success(false)
            }
        }
    }
}