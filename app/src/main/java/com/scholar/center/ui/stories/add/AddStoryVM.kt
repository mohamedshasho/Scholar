package com.scholar.center.ui.stories.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.domain.model.Resource
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStoryVM @Inject constructor(
    private val storyRepository: StoryRepository,
    private val dataStore: DataStorePreference,
) : ViewModel() {


    private val _selectedUri = MutableStateFlow<Uri>(Uri.EMPTY)
    val selectedUri = _selectedUri.asStateFlow()

    private val _isAddedSuccess = MutableStateFlow(false)
    val isAddedSuccess = _isAddedSuccess.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    private var filePath: String? = null


    fun setUri(uri: Uri, path: String?) {
        _selectedUri.value = uri
        filePath = path
    }


    fun addStory(title: String, description: String) {
        viewModelScope.launch {
            val studentId = dataStore.readValue(DataStorePreference.userId).first()
            if (studentId == null || studentId == 0) {
                _message.value = "Please sign in first"
                return@launch
            }
            when (val result =
                storyRepository.addStoryToNetwork(filePath, title, description, studentId)) {
                is Resource.Success -> {
                    result.data?.let { message ->
                        _message.value = message
                        _isAddedSuccess.value = true
                    }
                }
                is Resource.Error -> {
                    _message.value = result.message
                }
            }

        }
    }
}