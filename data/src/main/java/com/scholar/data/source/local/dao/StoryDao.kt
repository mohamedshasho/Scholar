package com.scholar.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.StoryLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao : BaseDao<StoryLocal> {

    @Query("select * from stories")
    fun getStories(): Flow<List<StoryLocal>>

    @Query("SELECT * FROM stories LIMIT :pageSize OFFSET :offset")
    suspend fun getStories(offset: Int, pageSize: Int): List<StoryLocal>

//    @Query("SELECT * FROM stories")
//    fun getStoriesPaging(): PagingSource<Int, StoryLocal>


    @Query("select * from stories where id=:id")
    fun getStory(id: Int): Flow<StoryLocal>

    @Query("DELETE from stories")
    suspend fun deleteAll()
}