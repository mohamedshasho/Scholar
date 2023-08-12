package com.scholar.center.ui.stories.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.domain.repo.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddStoryVM @Inject constructor(
    private val storyRepository: StoryRepository,
) : ViewModel() {


    private val _selectedUri = MutableStateFlow<Uri>(Uri.EMPTY)
    val selectedUri = _selectedUri.asStateFlow()


    private var filePath: String? = null


    fun setUri(uri: Uri, path: String?) {
        _selectedUri.value = uri
        filePath = path
    }


    fun addStory(title: String, description: String, studentId: Int) {
        viewModelScope.launch {

            storyRepository.addStoryToNetwork(filePath, title, description, studentId)

        }
    }
}