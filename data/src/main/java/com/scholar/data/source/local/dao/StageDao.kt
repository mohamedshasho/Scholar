package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.StageLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface StageDao : BaseDao<StageLocal> {

    @Query("select * from stages")
    fun observeStages() : Flow<List<StageLocal>>

    @Query("select name from stages where id=:id")
    suspend fun getName(id:Int) :String?
    @Query("delete from stages")
    suspend fun deleteAll()
}