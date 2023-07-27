package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.scholar.data.source.local.model.TeacherRemoteKeys

@Dao
interface TeacherRemoteKeysDao : BaseDao<TeacherRemoteKeys> {

    @Query("SELECT * FROM teacher_remote_keys WHERE id=:id")
    suspend fun getRemoteKeys(id: Int): TeacherRemoteKeys?

    @Query("DELETE from teacher_remote_keys")
    suspend fun deleteAll()
}