package com.scholar.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scholar.data.source.local.dao.CategoryDao
import com.scholar.data.source.local.dao.MaterialDao
import com.scholar.data.source.local.dao.StoryDao
import com.scholar.data.source.local.dao.TeacherDao
import com.scholar.data.source.local.model.CategoryLocal
import com.scholar.data.source.local.model.MaterialLocal
import com.scholar.data.source.local.model.StoryLocal
import com.scholar.data.source.local.model.TeacherLocal

@Database(
    entities = [
        CategoryLocal::class, MaterialLocal::class, StoryLocal::class, TeacherLocal::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ScholarDb : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun storyDao(): StoryDao
    abstract fun materialDao(): MaterialDao
    abstract fun teacherDao(): TeacherDao
}