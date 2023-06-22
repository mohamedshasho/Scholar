package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.scholar.data.source.local.model.StageLocal
import com.scholar.data.source.local.model.StageWithClassMates

@Dao
interface StageDao : BaseDao<StageLocal> {

    @Transaction
    @Query("SELECT * FROM stages")
    suspend fun getStageWithClassMates(): List<StageWithClassMates>
}