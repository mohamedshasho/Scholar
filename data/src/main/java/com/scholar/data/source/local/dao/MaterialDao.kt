package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.MaterialLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao : BaseDao<MaterialLocal> {

    @Query("select * from materials")
    fun getMaterials(): Flow<List<MaterialLocal>>

    @Query("select * from materials where id=:id")
    suspend fun getMaterial(id: Int): MaterialLocal

    @Query("select * from materials order by random() limit :limit")
    suspend fun getSomeMaterials(limit: Int): List<MaterialLocal>
}