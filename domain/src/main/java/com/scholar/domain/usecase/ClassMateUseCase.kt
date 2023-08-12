package com.scholar.domain.usecase

import com.scholar.domain.model.ClassRoom
import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.repo.ClassRoomRepository
import kotlinx.coroutines.flow.first

class ClassMateUseCase(
    private val repository: ClassRoomRepository,
    private val networkConnectivity: NetworkConnectivity,
) {
    suspend operator fun invoke(): List<ClassRoom> {
        if (networkConnectivity.isNetworkAvailable()) {
            repository.refresh()
        }
        return repository.observeClassesRooms().first()
    }
}