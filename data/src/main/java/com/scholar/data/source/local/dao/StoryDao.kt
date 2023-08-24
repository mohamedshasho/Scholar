package com.scholar.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.scholar.data.source.local.model.StoryLocal
import com.scholar.data.source.local.model.StoryWithStudentLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao : BaseDao<StoryLocal> {

    @Transaction
    @Query("select * from stories")
    fun getStories(): Flow<List<StoryWithStudentLocal>>

    @Query("SELECT * FROM stories LIMIT :pageSize OFFSET :offset")
    suspend fun getStories(offset: Int, pageSize: Int): List<StoryLocal>

    @Transaction
    @Query("select * from stories")
    fun getStoriesWithStudent(): PagingSource<Int, StoryWithStudentLocal>

    @Transaction
    @Query("select * from stories where id=:id")
    fun getStory(id: Int): Flow<StoryWithStudentLocal>

    @Query("DELETE from stories")
    suspend fun deleteAll()
}