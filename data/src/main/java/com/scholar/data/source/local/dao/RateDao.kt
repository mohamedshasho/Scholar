package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.RateLocal

@Dao
interface RateDao : BaseDao<RateLocal> {

    @Query("select AVG(rate) from rates where material_id=:id")
    suspend fun getTotalRateForMaterial(id: Int): Double
}