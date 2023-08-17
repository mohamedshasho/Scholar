package com.scholar.center.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.scholar.domain.model.*
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.MaterialRepository
import com.scholar.domain.usecase.CategoryUseCase
import com.scholar.domain.usecase.StageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val dataStore: DataStorePreference,
    private val categoryUseCase: CategoryUseCase,
    private val materialRepository: MaterialRepository,
    private val stageUseCase: StageUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _materials = MutableStateFlow<List<MaterialWithTeacher>>(emptyList())
    val materials = _materials.asStateFlow()

    private var myMaterials = HashMap<Int,Int>()

    private val _stages = MutableStateFlow<List<Stage>>(emptyList())
    val stages = _stages.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.readValue(DataStorePreference.myMaterials).collect {

            }
        }
        getStages()
        fetchData()
    }

    private fun getStages() {
        viewModelScope.launch {
            dataStore.saveValue(DataStorePreference.isAppFirstOpen, false)
            _stages.value = stageUseCase()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {

            launch {
                categoryUseCase().collect {
                    _categories.value = it
                }
            }
//            launch {
//                dataStore.readValue(DataStorePreference.myMaterials).collect{
//                    myMaterials = Gson().fromJson(it,Array<Int>::class.java).toList()
//                }
//            }
            launch {
                _loading.value = true



                materialRepository.getSomeMaterials().collect { materials ->
                    _materials.value = materials
//                    _materials.value.onEach {
//                        myMaterials.contains(it.material.id)
//                    }
                    _loading.value = false
                }
            }
        }
    }

}