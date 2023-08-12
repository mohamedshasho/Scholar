package com.scholar.center.ui.materials

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.unit.Constants.CATEGORY_ID_KEY
import com.scholar.center.unit.Constants.CLASSROOM_ID_KEY
import com.scholar.center.unit.Constants.STAGE_ID_KEY
import com.scholar.domain.model.*
import com.scholar.domain.repo.ClassRoomRepository
import com.scholar.domain.repo.MaterialRepository
import com.scholar.domain.usecase.CategoryUseCase
import com.scholar.domain.usecase.StageUseCase
import com.scholar.domain.usecase.SubjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.Lazy

@HiltViewModel
class MaterialsVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val classRoomRepository: ClassRoomRepository,
    private val categoryUseCase: CategoryUseCase,
    private val stageUseCase: StageUseCase,
    private val subjectUseCase: SubjectUseCase,
    private val materialRepository: Lazy<MaterialRepository>,
) : ViewModel() {

    val categoryId = savedStateHandle.get<Int>(CATEGORY_ID_KEY)
    val stageId = savedStateHandle.get<Int>(STAGE_ID_KEY)
    val classRoomId = savedStateHandle.get<Int>(CLASSROOM_ID_KEY)

    private val _materials = MutableStateFlow<List<Material>>(emptyList())
    val materials = _materials.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _stages = MutableStateFlow<List<Stage>>(emptyList())
    val stages = _stages.asStateFlow()

    private val _classrooms = MutableStateFlow<List<ClassRoom>>(emptyList())
    val classrooms = _classrooms.asStateFlow()

    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
    val subjects = _subjects.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    var selectedStage = 0
    var selectedClassRoom = 0
    var selectedCategory = 0
    var selectedSubject = 0

    init {
        getData()
        if (categoryId == null && stageId == null && classRoomId == null)// no filter applied
        {
            getMaterials()
        }
    }

    private fun getData() {
        viewModelScope.launch {
            _stages.value = stageUseCase()
            _classrooms.value = classRoomRepository.observeClassesRooms().first()
            _categories.value = categoryUseCase()
            _subjects.value = subjectUseCase()
        }
    }

    private fun getMaterials() {
        viewModelScope.launch {
            _loading.value = true
            when (val result = materialRepository.get().getMaterialsFromNetwork()) {
                is Resource.Success -> {
                    _loading.value = false
                    result.data?.let { list ->
                        _materials.value = list
                    }
                }
                is Resource.Error -> {
                    _loading.value = false
                }
            }
        }
    }
}