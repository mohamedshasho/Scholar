package com.scholar.center.ui.materials.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.unit.Constants.MATERIAL_ID_KEY
import com.scholar.domain.model.MaterialWithDetail
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.StudentRepository
import com.scholar.domain.usecase.MaterialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaterialDetailVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val materialUseCase: MaterialUseCase,
    private val dataStore: DataStorePreference,
    private val studentRepository: StudentRepository,
) : ViewModel() {


    private val materialId =
        requireNotNull(savedStateHandle.get<Int>(MATERIAL_ID_KEY)) { "Material id required" }

    private val _material = MutableStateFlow<MaterialWithDetail?>(null)
    val material = _material.asStateFlow()

    private val _isUserLogged = MutableStateFlow(false)
    val isUserLogged = _isUserLogged.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    init {

        viewModelScope.launch {
            launch {
                dataStore.readValue(DataStorePreference.isUserLoggedIn).collect {
                    _isUserLogged.value = it ?: false
                }
            }
            launch {
                _loading.value = true
                materialUseCase(materialId).collect {
                    _material.value = it
                    _loading.value = false
                }
            }
        }
    }

    fun purchase() {
        viewModelScope.launch {
            _loading.value = true
            val studentID = dataStore.readValue(DataStorePreference.userId).first()
            if (studentID == null || studentID == 0) return@launch
            _loading.value = true
            when(val result = studentRepository.purchaseMaterial(studentID, materialId)){
                is Resource.Success->{
                    _message.value = result.data
                }
                is Resource.Error->{
                    _message.value = result.message

                }
            }
            _loading.value = false
        }
    }
}