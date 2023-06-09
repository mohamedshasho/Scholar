package com.scholar.center.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.domain.model.Category
import com.scholar.domain.model.Material
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Stage
import com.scholar.domain.repo.MaterialRepository
import com.scholar.domain.usecase.CategoryUseCase
import com.scholar.domain.usecase.StageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val categoryUseCase: CategoryUseCase,
    private val materialRepository: MaterialRepository,
    private val stageUseCase: StageUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _materials = MutableStateFlow<List<Material>>(emptyList())
    val materials = _materials.asStateFlow()

    private val _stages = MutableStateFlow<List<Stage>>(emptyList())
    val stages = _stages.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _loading.value = true
            val categories = async {
                categoryUseCase()
            }
            val materials = async {
                when (val result = materialRepository.getSomeMaterials()) {
                    is Resource.Success -> {
                        result.data
                    }
                    is Resource.Error -> {
                        showSnackBarError(result.message)
                        emptyList()
                    }
                }
            }
            val stages = async { stageUseCase() }
            updateData(categories.await(), stages.await(), materials.await())
            _loading.value = false
        }
    }

    private fun updateData(
        c: List<Category>,
        stages: List<Stage>,
        materials: List<Material>?,
    ) {
        _categories.value = c
        _stages.value = stages
        if (materials != null) {
            _materials.value = materials
        }
    }

    private fun showSnackBarError(message: String?) {
        // TODO("Not yet implemented")
    }


}