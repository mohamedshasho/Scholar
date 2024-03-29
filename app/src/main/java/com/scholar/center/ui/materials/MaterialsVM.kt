package com.scholar.center.ui.materials

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scholar.center.unit.Constants.CATEGORY_ID_KEY
import com.scholar.center.unit.Constants.CLASSROOM_ID_KEY
import com.scholar.center.unit.Constants.SEARCH_FOCUS_KEY
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

@HiltViewModel
class MaterialsVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val classRoomRepository: ClassRoomRepository,
    private val categoryUseCase: CategoryUseCase,
    private val stageUseCase: StageUseCase,
    private val subjectUseCase: SubjectUseCase,
    private val materialRepository: MaterialRepository,
) : ViewModel() {

    private val focusOnSearch = savedStateHandle.get<Boolean>(SEARCH_FOCUS_KEY) ?: false
    private val categoryId = savedStateHandle.get<Int>(CATEGORY_ID_KEY)
    private val stageId = savedStateHandle.get<Int>(STAGE_ID_KEY)
    private val classRoomId = savedStateHandle.get<Int>(CLASSROOM_ID_KEY)

    private val _materials = MutableStateFlow<PagingData<MaterialWithTeacher>>(PagingData.empty())
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

    var selectedStage = stageId ?: 0
    var selectedClassRoom = classRoomId ?: 0
    var selectedCategory = categoryId ?: 0
    var selectedSubject = 0

    init {
        getFilterData()
        if (!focusOnSearch) {
            getMaterials()
        }
    }

    private fun getFilterData() {
        viewModelScope.launch {
            launch {
                categoryUseCase().collect {
                    _categories.value = it
                }
            }
            _stages.value = stageUseCase()
            _classrooms.value = classRoomRepository.observeClassesRooms().first()
            _subjects.value = subjectUseCase()
        }
    }

    fun getMaterials() {
        viewModelScope.launch {
            materialRepository.filterMaterialFromNetwork(
                stageId = if (selectedStage == 0) null else selectedStage,
                classroomId = if (selectedClassRoom == 0) null else selectedClassRoom,
                subjectId = if (selectedSubject == 0) null else selectedSubject,
                categoryId = if (selectedCategory == 0) null else selectedCategory,
            ).cachedIn(viewModelScope)
                .collect { pagingData ->
                    _materials.value = pagingData
                }
        }
    }

    fun search(key: String?) {
        if (key.isNullOrEmpty()) return
        viewModelScope.launch {
            materialRepository.searchForMaterialFromNetwork(key)
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _materials.value = pagingData
                }
        }
    }
}