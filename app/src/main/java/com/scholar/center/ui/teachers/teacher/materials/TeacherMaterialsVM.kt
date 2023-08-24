package com.scholar.center.ui.teachers.teacher.materials

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.domain.model.MaterialWithTeacher
import com.scholar.domain.repo.MaterialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeacherMaterialsVM @Inject constructor(
    private val materialRepository: MaterialRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<MaterialWithTeacher>>(emptyList())
    val uiState = _uiState.asStateFlow()

    fun getMaterials(id: Int) {
        viewModelScope.launch {
            materialRepository.getMaterialsTeacher(id).collect {
                _uiState.value = it
            }
        }
    }

}