package com.scholar.domain.usecase

import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.model.Subject
import com.scholar.domain.repo.SubjectRepository
import kotlinx.coroutines.flow.first

class SubjectUseCase(
    private val repository: SubjectRepository,
    private val networkConnectivity: NetworkConnectivity,
) {
    suspend operator fun invoke(): List<Subject> {
        if (networkConnectivity.isNetworkAvailable()) {
            repository.refresh()
        }
        return repository.getSubjects().first()
    }
}