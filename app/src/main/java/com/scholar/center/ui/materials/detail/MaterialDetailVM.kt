package com.scholar.center.ui.materials.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.scholar.center.unit.Constants.MATERIAL_ID_KEY
import com.scholar.domain.model.MaterialWithDetail
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.MaterialRepository
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
    private val materialRepository: MaterialRepository
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


    private var rate: Int? = null
    private var comment: String? = null

    private val _alreadyBought = MutableStateFlow(false)
    val alreadyBought = _alreadyBought.asStateFlow()

    private val _alreadyRated = MutableStateFlow(false)
    val alreadyRated = _alreadyRated.asStateFlow()

    init {

        viewModelScope.launch {
            launch {
                dataStore.readValue(DataStorePreference.myMaterials).collect {
                    if (it != null) {
                        val myMaterials = Gson().fromJson(it, Array<Int>::class.java)
                        if (myMaterials.contains(materialId)) {
                            _alreadyBought.value = true
                        }
                    }
                }
            }

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

            launch {
                dataStore.readValue(DataStorePreference.myRates).collect {
                    if (it != null) {
                        val myRates = Gson().fromJson(it, Array<Int>::class.java)
                        if (myRates.contains(materialId)) {
                            _alreadyRated.value = true
                        }
                    }
                }
            }
        }
    }


    fun rate(rate: Int, comment: String) {
        if (rate == 0 || comment.isEmpty()) return

        viewModelScope.launch {
            val studentID = dataStore.readValue(DataStorePreference.userId).first()
            if (studentID == null || studentID == 0) return@launch
            materialRepository.rateMaterial(studentID, materialId, rate, comment)
            val gson = Gson()
            val myRatesString = dataStore.readValue(DataStorePreference.myRates).first()
            val myRates = gson.fromJson(myRatesString, Array<Int>::class.java)
            val newRates = myRates.toMutableList()
            newRates.add(materialId)
            val newRatesString = gson.toJson(newRates)
            dataStore.saveValue(DataStorePreference.myRates, newRatesString)
        }


    }

    fun purchase() {
        viewModelScope.launch {
            _loading.value = true
            val studentID = dataStore.readValue(DataStorePreference.userId).first()
            if (studentID == null || studentID == 0) return@launch
            when (val result = studentRepository.purchaseMaterial(studentID, materialId)) {
                is Resource.Success -> {
                    _message.value = result.data
                }

                is Resource.Error -> {
                    _message.value = result.message

                }
            }
            _loading.value = false
        }
    }
}