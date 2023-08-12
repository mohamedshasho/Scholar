package com.scholar.center.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val studentRepository: StudentRepository,
    private val dataStore: DataStorePreference,
) : ViewModel() {

    private val _studentName = MutableStateFlow<String?>(null)
    val studentName = _studentName.asStateFlow()


    init {
        viewModelScope.launch {
            val studentId = dataStore.readValue(DataStorePreference.userId).first()
            studentId?.let {
                _studentName.value = studentRepository.getStudentName(it)
            }
        }
    }
}