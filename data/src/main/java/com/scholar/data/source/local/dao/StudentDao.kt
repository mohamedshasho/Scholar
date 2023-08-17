package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.scholar.data.source.local.model.StudentLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao : BaseDao<StudentLocal> {

    @Query("UPDATE students SET wallet = wallet + :wallet, fullName=:name,image=:image WHERE id = :studentId")
    suspend fun updateWallet(wallet: Int, studentId: Int, name: String, image: String)

    @Query("UPDATE students SET wallet = wallet + :wallet, fullName=:name WHERE id = :studentId")
    suspend fun updateWallet(wallet: Int, studentId: Int, name: String)


    @Query("UPDATE students SET wallet = wallet - :wallet WHERE id = :studentId")
    suspend fun updateWallet(wallet: Int, studentId: Int)


    @Query("select fullName from students where id=:id")
    suspend fun getStudentName(id: Int): String?

    @Query("select * from students where id=:id")
    fun getStudent(id: Int): Flow<StudentLocal?>
}