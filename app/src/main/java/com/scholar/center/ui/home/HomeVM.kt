package com.scholar.center.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.domain.model.Category
import com.scholar.domain.usecase.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val categoryUseCase: CategoryUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _categories.value = categoryUseCase()
        }
    }

}