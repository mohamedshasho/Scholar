package com.scholar.center.ui.materials.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.unit.Constants.MATERIAL_ID_KEY
import com.scholar.domain.model.Material
import com.scholar.domain.model.MaterialWithDetail
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.MaterialRepository
import com.scholar.domain.usecase.MaterialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaterialDetailVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val materialUseCase: MaterialUseCase,
) : ViewModel() {

    private val materialId =
        requireNotNull(savedStateHandle.get<Int>(MATERIAL_ID_KEY)) { "Material id required" }

    private val _material = MutableStateFlow<MaterialWithDetail?>(null)
    val material = _material.asStateFlow()


    init {
        viewModelScope.launch {
            materialUseCase(materialId).collect {
                _material.value = it
            }
        }
    }
}