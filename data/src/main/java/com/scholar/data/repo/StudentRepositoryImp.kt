package com.scholar.data.repo

import com.google.gson.Gson
import com.scholar.data.source.local.dao.StudentDao
import com.scholar.data.source.local.model.toLocal
import com.scholar.data.source.network.StudentNetworkDataSource
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.StudentRepository
import kotlinx.coroutines.flow.first

class StudentRepositoryImp(
    private val dataStore: DataStorePreference,
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
                studentDao.updateWallet(amount, studentId)
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
                val myMaterialIds = dataStore.readValue(DataStorePreference.myMaterials).first()
                if (myMaterialIds == null) {
                    val newMaterialIds = listOf(materialId)
                    val gson = Gson()
                    val myMaterials = gson.toJson(newMaterialIds)
                    dataStore.saveValue(DataStorePreference.myMaterials, myMaterials)
                } else {
                    val gson = Gson()
                    val m = gson.fromJson(myMaterialIds, Array<Int>::class.java)
                    val newMaterialIds = m.toMutableList()
                    newMaterialIds.add(materialId)
                    val myMaterialsString = gson.toJson(newMaterialIds)
                    dataStore.saveValue(DataStorePreference.myMaterials, myMaterialsString)
                }
                Resource.Success(response.data?.message)
            }

            is Resource.Error -> {
                Resource.Error(response.message)
            }
        }
    }

    override suspend fun syncStudent(studentId: Int) {
        when (val response = networkDataSource.sync(studentId)) {
            is Resource.Success -> {
                response.data?.let { student ->
                    studentDao.upsert(student.toLocal())
                    val myMaterialIds = student.material?.map { it.id }
                    val gson = Gson()
                    val myMaterials = gson.toJson(myMaterialIds)
                    dataStore.saveValue(DataStorePreference.myMaterials, myMaterials)
                }
            }
            is Resource.Error -> {}
        }
    }

    override suspend fun update(
        studentId: Int,
        imagePath: String,
        fullName: String,
        phone: String,
        birth: String
    ): Resource<String> {
        return networkDataSource.update(studentId, imagePath, fullName, phone, birth)
    }
}