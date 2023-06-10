package com.scholar.center.ui.teachers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.model.UiState
import com.scholar.domain.model.Teacher
import com.scholar.domain.repo.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeachersVM @Inject constructor(
    private val teacherRepository: TeacherRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Teacher>>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()


    init {
        getTeachers()
    }

    private fun getTeachers() {
        viewModelScope.launch {
            teacherRepository.observeTeachers()
        }
    }
}