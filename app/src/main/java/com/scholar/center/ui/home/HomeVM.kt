package com.scholar.center.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.model.UiState
import com.scholar.domain.model.Category
import com.scholar.domain.model.Resource
import com.scholar.domain.usecase.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val categoryUseCase: CategoryUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow<UiState<List<Category>>>(UiState.Idle)
    val categories: StateFlow<UiState<List<Category>>> = _categories

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {

            _categories.update { UiState.Loading }

            when (val result = categoryUseCase()) {
                is Resource.Success -> {

                    result.data?.let { list ->
                        _categories.update { UiState.Success(list) }
                    }
                }
                is Resource.Error -> {

                }
            }
        }
    }

}