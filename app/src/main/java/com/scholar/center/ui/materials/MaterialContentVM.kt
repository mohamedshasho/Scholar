package com.scholar.center.ui.materials

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.unit.Constants
import com.scholar.domain.model.MaterialWithDetail
import com.scholar.domain.repo.MaterialRepository
import com.scholar.domain.usecase.MaterialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class MaterialContentVM @Inject constructor(
    private val materialUseCase: MaterialUseCase,
    savedStateHandle: SavedStateHandle,
    private val materialRepository: MaterialRepository,
) : ViewModel() {
    private val materialId =
        requireNotNull(savedStateHandle.get<Int>(Constants.MATERIAL_ID_KEY)) { "Material id required" }


    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    private val _material = MutableStateFlow<MaterialWithDetail?>(null)
    val material = _material.asStateFlow()


    private val _byteArray = MutableStateFlow<File?>(null)
    val byteArray = _byteArray.asStateFlow()

    init {
        viewModelScope.launch {
            _loading.value = true
            materialUseCase(materialId).collect {
                _material.value = it
                _loading.value = false
            }
        }
    }


    fun downloadPdf(url: URL) {
        viewModelScope.launch {
            materialRepository.loadPDf(url) { file ->
                _byteArray.value = file
            }
        }
    }
}