package com.scholar.center.ui.teachers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.scholar.domain.repo.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeachersVM @Inject constructor(
    private val teacherRepository: TeacherRepository,
) : ViewModel() {


    val teachers = teacherRepository.getTeachersPagination()
        .cachedIn(viewModelScope)


}