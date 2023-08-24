package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.CategoryLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : BaseDao<CategoryLocal> {

    @Query("select * from categories")
    fun getCategories() : Flow<List<CategoryLocal>>


    @Query("select name from categories where id=:id")
    suspend fun getName(id:Int) :String?
}