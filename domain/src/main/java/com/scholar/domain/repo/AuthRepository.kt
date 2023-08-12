package com.scholar.domain.repo

import com.scholar.domain.model.Resource
import com.scholar.domain.model.Student

interface AuthRepository {

    suspend fun login(email: String, password: String): Resource<Int>
    suspend fun register(
        fullName: String,
        email: String,
        password: String,
    ): Resource<Int>

    suspend fun logout(): Resource<Boolean>

}