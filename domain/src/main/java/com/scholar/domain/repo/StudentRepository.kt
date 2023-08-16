package com.scholar.domain.repo

import com.scholar.domain.model.Resource
import com.scholar.domain.model.Student
import kotlinx.coroutines.flow.Flow


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

    suspend fun purchaseMaterial(
        studentId: Int,
        materialId: Int,
    ): Resource<String>
}