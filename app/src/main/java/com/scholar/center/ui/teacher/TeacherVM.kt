package com.scholar.center.ui.teacher

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.scholar.center.unit.Constants.TEACHER_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TeacherVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val teacherID = requireNotNull(savedStateHandle.get<Int>(TEACHER_ID_KEY))

    private val _teacher = MutableStateFlow<String>("Teacher name")
    val teacher = _teacher.asStateFlow()

    init {
        getTeacherById(teacherID)
    }

    private fun getTeacherById(teacherID: Int) {

    }
}
