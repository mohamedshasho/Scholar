package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.CategoryLocal
import com.scholar.data.source.local.model.MaterialLocal
import com.scholar.data.source.local.model.StoryLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao : BaseDao<StoryLocal> {

    @Query("select * from stories")
    fun getStories(): Flow<List<StoryLocal>>
}