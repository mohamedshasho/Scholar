package com.scholar.data.repo

import com.scholar.data.source.local.dao.StudentDao
import com.scholar.data.source.network.StudentNetworkDataSource
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.StudentRepository

class StudentRepositoryImp(
    private val networkDataSource: StudentNetworkDataSource,
    private val studentDao: StudentDao,
) : StudentRepository {
    override suspend fun getStudentName(id: Int): String? {
        return studentDao.getStudentName(id)
    }

    override fun getStudent(id: Int) = studentDao.getStudent(id)


    override suspend fun purchaseCredit(
        filePath: String,
        studentId: Int,
        amount: Int,
        paymentMethod: Int,
        description: String?
    ): Resource<String> {
        return when (val response = networkDataSource.purchaseCredit(
            filePath,
            studentId,
            amount,
            paymentMethod,
            description
        )) {
            is Resource.Success -> {
                Resource.Success(response.data?.message)
            }

            is Resource.Error -> {
                Resource.Error(response.message)
            }
        }
    }

    override suspend fun purchaseMaterial(studentId: Int, materialId: Int): Resource<String> {
        return when (val response = networkDataSource.purchaseMaterial(studentId, materialId)) {
            is Resource.Success -> {
                Resource.Success(response.data?.message)
            }
            is Resource.Error -> {
                Resource.Error(response.message)
            }
        }
    }
}