package com.scholar.center.ui.subject

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SubjectsVM  @Inject constructor() : ViewModel() {

    init {
        Log.d("lifecycleViewModel", "create Subjects VM: ")
    }


    fun getN() = 5

    override fun onCleared() {
        super.onCleared()
        Log.d("lifecycleViewModel", "onCleared Subjects VM: ")
    }

}