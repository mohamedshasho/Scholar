package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.StageLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface StageDao : BaseDao<StageLocal> {

//    @Transaction
//    @Query("SELECT * FROM stages")
//    suspend fun getStageWithClassMates(): List<StageWithClassMates>
    @Query("select * from stages")
    fun observeStages() : Flow<List<StageLocal>>
    @Query("delete from stages")
    suspend fun deleteAll()
}