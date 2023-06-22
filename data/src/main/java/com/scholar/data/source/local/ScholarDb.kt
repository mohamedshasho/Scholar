package com.scholar.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scholar.data.source.local.dao.*
import com.scholar.data.source.local.model.*

@Database(
    entities = [
        CategoryLocal::class, MaterialLocal::class, StoryLocal::class, TeacherLocal::class,
        SubjectLocal::class, StageLocal::class, ClassMateLocal::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ScholarDb : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun storyDao(): StoryDao
    abstract fun materialDao(): MaterialDao
    abstract fun teacherDao(): TeacherDao
    abstract fun stageDao(): StageDao
    abstract fun classMateDao(): ClassMateDao
}