package com.scholar.center.ui.teachers.teachersSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scholar.domain.model.TeacherWithSubjects
import com.scholar.domain.repo.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeachersSearchVM @Inject constructor(
    private val teacherRepository: TeacherRepository,
) : ViewModel() {

    private val _teachersWithSubjects =
        MutableStateFlow<PagingData<TeacherWithSubjects>>(PagingData.empty())
    val teachers = _teachersWithSubjects.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()


    fun searchForTeachers(input: String?) {
        if (input.isNullOrEmpty()) return
        viewModelScope.launch {
            _loading.value = true
            teacherRepository.searchForTeachers(input.lowercase()).cachedIn(viewModelScope)
                .collect { data ->
                    _teachersWithSubjects.value = data

                }
        }
    }

    fun changeLoadingState(b: Boolean) {
        _loading.value = b
    }


}