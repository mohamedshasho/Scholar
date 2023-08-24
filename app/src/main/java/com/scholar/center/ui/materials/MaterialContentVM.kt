package com.scholar.center.ui.materials

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.unit.Constants
import com.scholar.domain.model.MaterialWithDetail
import com.scholar.domain.usecase.MaterialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaterialContentVM @Inject constructor(
    private val materialUseCase: MaterialUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val materialId =
        requireNotNull(savedStateHandle.get<Int>(Constants.MATERIAL_ID_KEY)) { "Material id required" }


    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    private val _material = MutableStateFlow<MaterialWithDetail?>(null)
    val material = _material.asStateFlow()

    init {
        viewModelScope.launch {
            _loading.value = true
            _material.value =  materialUseCase(materialId)
            _loading.value = false
        }
    }
}