package com.scholar.domain.repo

import com.scholar.domain.model.MessageResponse
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Student
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field


interface StudentRepository {

    suspend fun getStudentName(id: Int): String?
    fun getStudent(id: Int): Flow<Student?>


    suspend fun purchaseCredit(
        filePath: String,
        studentId: Int,
        amount: Int,
        paymentMethod: Int,
        description: String?,
    ): Resource<String>
}