package com.scholar.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scholar.data.source.local.dao.*
import com.scholar.data.source.local.model.*

@Database(
    entities = [
        CategoryLocal::class, MaterialLocal::class, StoryLocal::class, TeacherLocal::class,
        SubjectLocal::class, StageLocal::class, ClassMateLocal::class,
        StoryRemoteKeys::class, TeacherRemoteKeys::class, TeacherSubjectCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ScholarDb : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun subjectDao(): SubjectDao
    abstract fun storyDao(): StoryDao
    abstract fun materialDao(): MaterialDao
    abstract fun teacherDao(): TeacherDao
    abstract fun stageDao(): StageDao

    abstract fun storyRemoteKeysDao(): StoryRemoteKeysDao
    abstract fun teacherRemoteKeysDao(): TeacherRemoteKeysDao

    abstract fun classMateDao(): ClassMateDao
}