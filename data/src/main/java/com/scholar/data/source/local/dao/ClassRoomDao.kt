package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.ClassRoomLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassRoomDao : BaseDao<ClassRoomLocal> {

    @Query("select * from classes")
    fun observeClassesMate(): Flow<List<ClassRoomLocal>>

    @Query("delete from classes")
    suspend fun deleteAll()
}