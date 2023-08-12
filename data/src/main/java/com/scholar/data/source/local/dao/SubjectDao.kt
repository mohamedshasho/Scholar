package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.scholar.data.source.local.model.SubjectLocal
import com.scholar.data.source.local.model.TeacherSubjectCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao : BaseDao<SubjectLocal> {

    @Query("select * from subjects")
    fun getSubjects() : Flow<List<SubjectLocal>>


    @Insert(onConflict = REPLACE)
    suspend fun insertTeacherSubject(teacherSubjectCrossRef: TeacherSubjectCrossRef)



    @Query("delete from subjects")
    suspend fun deleteAll()
}