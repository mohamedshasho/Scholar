package com.scholar.center.ui.classes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.unit.Constants.STAGE_ID_KEY
import com.scholar.domain.model.ClassRoom
import com.scholar.domain.usecase.ClassMateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val classMateUseCase: ClassMateUseCase,
) : ViewModel() {

    private val stageId = requireNotNull(savedStateHandle.get<Int>(STAGE_ID_KEY))

    private val _classRooms = MutableStateFlow<List<ClassRoom>>(emptyList())
    val classRooms = _classRooms.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()


    init {
        viewModelScope.launch {
            _loading.value = true
            _classRooms.value = if (stageId == 1) classMateUseCase() else classMateUseCase()
                .filter { it.id < 4 }
            _loading.value = false
        }
    }

}