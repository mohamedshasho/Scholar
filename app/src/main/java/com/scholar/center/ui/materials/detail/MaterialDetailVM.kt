package com.scholar.center.ui.materials.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.unit.Constants.MATERIAL_ID_KEY
import com.scholar.domain.model.Material
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.MaterialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaterialDetailVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val materialRepository: MaterialRepository,
) : ViewModel() {

    private val materialId =
        requireNotNull(savedStateHandle.get<Int>(MATERIAL_ID_KEY)) { "Material id required" }

    private val _material = MutableStateFlow<Material?>(null)
    val material = _material.asStateFlow()


    init {
        viewModelScope.launch {
            when (val result = materialRepository.getMaterialFromNetwork(materialId)) {
                is Resource.Success -> {
                    _material.value = result.data
                }
                is Resource.Error -> {}
            }
        }
    }
}