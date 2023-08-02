package com.scholar.center.ui.teacher.materials

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.model.UiState
import com.scholar.domain.model.Material
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.MaterialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeacherMaterialsVM @Inject constructor(
    private val materialRepository: MaterialRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Material>>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        getMaterials()
    }


    private fun getMaterials() {
        viewModelScope.launch {
            _uiState.update { UiState.Loading }
            when (val result = materialRepository.getMaterialsFromNetwork()) {
                is Resource.Success -> {
                    result.data?.let { list ->
                        _uiState.update {
                            UiState.Success(list)
                        }
                    }
                }
                is Resource.Error -> {
                    _uiState.update { UiState.Error(result.message) }
                }
            }
        }
    }

}