package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.scholar.data.source.local.model.MaterialLocal
import com.scholar.data.source.local.model.MaterialWithTeacherLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao : BaseDao<MaterialLocal> {

    @Query("select * from materials")
    fun getMaterials(): Flow<List<MaterialLocal>>

    @Query("select * from materials where id=:id")
    suspend fun getMaterial(id: Int): MaterialLocal

    @Query("select * from materials order by random() limit :limit")
    suspend fun getSomeMaterials(limit: Int): List<MaterialLocal>

    @Transaction
    @Query(
        "SELECT materials.*, teachers.teacher_id as id ,teachers.full_name as name, teachers.image as image" +
                " FROM materials " +
                "LEFT JOIN teachers ON materials.subjectId = subjects.id"
    )
    fun getAllMaterialsWithSubject(): Flow<List<MaterialWithTeacherLocal>>
}