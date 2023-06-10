package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.CategoryLocal
import com.scholar.data.source.local.model.MaterialLocal
import com.scholar.data.source.local.model.TeacherLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface TeacherDao : BaseDao<TeacherLocal> {

    @Query("select * from teachers")
    fun getTeachers(): Flow<List<TeacherLocal>>


    @Query("select * from teachers where id=:id")
    suspend fun getTeacherById(id: Int): TeacherLocal

    @Query("delete from teachers")
    suspend fun deleteAll()
}