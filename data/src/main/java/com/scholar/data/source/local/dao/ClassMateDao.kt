package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.ClassMateLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassMateDao : BaseDao<ClassMateLocal> {

    @Query("select * from classes where stageId=:id")
    fun observeClassesMate(id: Int): Flow<List<ClassMateLocal>>

    @Query("delete from classes")
    suspend fun deleteAll()
}