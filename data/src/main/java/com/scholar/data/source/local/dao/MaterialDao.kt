package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.scholar.data.source.local.model.MaterialLocal
import com.scholar.data.source.local.model.MaterialWithDetailLocal
import com.scholar.data.source.local.model.MaterialWithTeacherLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao : BaseDao<MaterialLocal> {

    @Query("select * from materials")
    fun getMaterials(): Flow<List<MaterialLocal>>


    @Transaction
    @Query(
        "SELECT materials.id,materials.title, materials.description,materials.price, materials.discount," +
                " materials.hoursNumberOfWeek, materials.categoryId , " +
                " teachers.teacher_id as teacherId ,teachers.full_name as name, teachers.image as image" +
                ", subjects.name as subject, stages.name as stage, classes.name as classroom " +
                " FROM materials " +
                "LEFT JOIN teachers ON materials.teacher_id = teachers.teacher_id " +
                "join classes on materials.class_id = classes.id " +
                "join stages on materials.stage_id = stages.id " +
                "join subjects on materials.subject_id = subjects.subject_id " +
                "where materials.id = :id"
    )
    fun getMaterial(id: Int): Flow<MaterialWithDetailLocal>

    @Transaction
    @Query(
        "SELECT materials.id,materials.title, materials.description,materials.price, materials.discount," +
                " materials.hoursNumberOfWeek, materials.categoryId , " +
                " teachers.teacher_id as teacherId ,teachers.full_name as name, teachers.image as image," +
                " (select avg(rate) from rates where material_id=materials.id) as totalRate" +
                " FROM materials " +
                "LEFT JOIN teachers ON materials.teacher_id = teachers.teacher_id "+
                "order by random() limit :limit"
    )
    fun getSomeMaterials(limit: Int): Flow<List<MaterialWithTeacherLocal>>

    @Transaction
    @Query(
        "SELECT materials.id,materials.title, materials.description,materials.price, materials.discount," +
                " materials.hoursNumberOfWeek, materials.categoryId , " +
                " teachers.teacher_id as teacherId ,teachers.full_name as name, teachers.image as image," +
                " (select avg(rate) from rates where material_id=materials.id) as totalRate" +
                " FROM materials " +
                "LEFT JOIN teachers ON materials.teacher_id = teachers.teacher_id "
    )
    fun getAllMaterialsWithSubject(): Flow<List<MaterialWithTeacherLocal>>
}