package com.scholar.center.ui.subjects

import android.util.Log
import androidx.lifecycle.ViewModel
import com.scholar.data.model.CategoryLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SubjectsVM  @Inject constructor() : ViewModel() {

    val mSubjects = listOf(
        CategoryLocal(name = "name1", image = ""),
        CategoryLocal(name = "name2", image = ""),
        CategoryLocal(name = "name3", image = ""),
        CategoryLocal(name = "name3", image = ""),
        CategoryLocal(name = "name3", image = ""),
        CategoryLocal(name = "name3", image = ""),
        CategoryLocal(name = "name3", image = ""),
        CategoryLocal(name = "name3", image = ""),
        CategoryLocal(name = "name4", image = "")
    )

    fun getN() = 5

    override fun onCleared() {
        super.onCleared()
        Log.d("lifecycleViewModel", "onCleared Subjects VM: ")
    }

}