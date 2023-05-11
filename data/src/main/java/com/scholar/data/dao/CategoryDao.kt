package com.scholar.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.model.CategoryLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : BaseDao<CategoryLocal> {

    @Query("select * from categories")
    fun getCategories() : List<CategoryLocal>
}