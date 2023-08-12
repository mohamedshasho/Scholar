package com.scholar.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.scholar.data.source.local.model.TeacherLocal
import com.scholar.data.source.local.model.TeacherWithSubjectsLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface TeacherDao : BaseDao<TeacherLocal> {

    @Query("select * from teachers")
    fun getTeachers(): Flow<List<TeacherLocal>>


    @Query("select * from teachers")
    fun getTeachersPaging(): PagingSource<Int, TeacherWithSubjectsLocal>

    @Transaction
    @Query("SELECT * FROM teachers LIMIT :pageSize OFFSET :offset")
    suspend fun getTeachers(offset: Int, pageSize: Int): List<TeacherWithSubjectsLocal>

    @Query("select * from teachers where teacher_id=:id")
    suspend fun getTeacherById(id: Int): TeacherLocal


    @Transaction
    @Query("select * from teachers where teacher_id=:id")
    suspend fun getTeacherWithSubjects(id: Int): TeacherWithSubjectsLocal

    @Query("delete from teachers")
    suspend fun deleteAll()
}