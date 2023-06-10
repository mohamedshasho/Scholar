package com.scholar.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.filters.MediumTest
import com.scholar.data.source.local.ScholarDb
import com.scholar.data.source.local.model.TeacherLocal
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@MediumTest
class TeacherDaoTest {
    private lateinit var database: ScholarDb

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            ScholarDb::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun clear(){
        database.close()
    }

    @Test
    fun insertTaskAndGetTasks() = runBlocking {

        val teacher = TeacherLocal(
            name = "name",
            age = 22,
            skills = "s",
            views = 2,
            qualification = "qu",
            lastSeen = null,
            profile = null,
            bio = "bio",
            id = 1
        )
        database.teacherDao().upsert(teacher)

        val teachers = database.teacherDao().getTeachers().first()

        assertEquals(1, teachers.size)
        assertEquals(teacher, teachers[0])
    }
}