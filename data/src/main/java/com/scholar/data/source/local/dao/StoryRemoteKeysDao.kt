package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.StoryRemoteKeys

@Dao
interface StoryRemoteKeysDao : BaseDao<StoryRemoteKeys> {

    @Query("SELECT * FROM story_remote_keys WHERE id=:id")
    suspend fun getRemoteKeys(id: Int): StoryRemoteKeys?

    @Query("DELETE from story_remote_keys")
    suspend fun deleteAll()
}