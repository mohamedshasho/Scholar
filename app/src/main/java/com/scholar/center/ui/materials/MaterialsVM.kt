package com.scholar.center.ui.materials

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.model.UiState
import com.scholar.domain.model.Category
import com.scholar.domain.model.Material
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Stage
import com.scholar.domain.repo.CategoryRepository
import com.scholar.domain.repo.MaterialRepository
import com.scholar.domain.repo.StageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MaterialsVM @Inject constructor(
    private val materialRepository: MaterialRepository,
    private val categoryRepository: CategoryRepository,
    private val stageRepository: StageRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Material>>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()


    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _stages = MutableStateFlow<List<Stage>>(emptyList())
    val stages = _stages.asStateFlow()

    init {
        getMaterials()
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _categories.value = categoryRepository.observeCategories().first()
            _stages.value = stageRepository.observeStages().first()
        }
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