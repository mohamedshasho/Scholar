package com.scholar.center.ui.teachers.teacher

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.unit.Constants.TEACHER_ID_KEY
import com.scholar.domain.model.Teacher
import com.scholar.domain.repo.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val teacherRepository: TeacherRepository,
) : ViewModel() {

    private val teacherID =
        requireNotNull(savedStateHandle.get<Int>(TEACHER_ID_KEY)) { "Teacher id required" }

    private val _teacher = MutableStateFlow<Teacher?>(null)
    val teacher = _teacher.asStateFlow()

    init {
        getTeacherById(teacherID)
    }

    private fun getTeacherById(teacherID: Int) {
        viewModelScope.launch {
            teacherRepository.getTeacherByIdFromLocal(teacherID)?.let { teacher ->
                _teacher.value = teacher
            }
        }
    }
}
