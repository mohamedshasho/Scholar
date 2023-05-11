package com.scholar.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scholar.data.dao.CategoryDao
import com.scholar.data.model.CategoryLocal

@Database(
    entities = [
        CategoryLocal::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ScholarDb : RoomDatabase() {
    abstract fun categoryDao() : CategoryDao
}