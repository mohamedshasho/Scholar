package com.scholar.data.source.network

import com.scholar.data.service.ApiService
import com.scholar.domain.model.TeacherNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class TeacherNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
){
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    private fun dummyData() =
        listOf(
            TeacherNetwork(
                id = 1,
                name = "teacher 1",
                age = 22,
                bio = "bio 1",
                profile = null,
                lastSeen = "2020",
                qualification = "Quil",
                skills = "Skills",
                views = 2
            ),
            TeacherNetwork(
                id = 1,
                name = "teacher 2",
                age = 25,
                bio = "bio 1",
                profile = null,
                lastSeen = "2022",
                qualification = "Quil",
                skills = "Skills",
                views = 2
            ),
        )

    suspend fun loadTeachers(): List<TeacherNetwork> = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        val response = apiService.getTeachers()
        return dummyData()
    }
}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L