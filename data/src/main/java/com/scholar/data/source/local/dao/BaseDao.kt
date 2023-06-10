package com.scholar.data.source.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import androidx.room.Delete


@Dao
interface BaseDao<T> {

    @Upsert
    suspend fun upsert(vararg obj: T)

    @Upsert
    suspend fun upsert(obj: T)

    @Upsert
    suspend fun upsertAll(list: List<T>)

    @Delete
    suspend fun delete(obj: T)
}