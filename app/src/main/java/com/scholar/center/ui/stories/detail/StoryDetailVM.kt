package com.scholar.center.ui.stories.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scholar.center.unit.Constants.STORY_ID_KEY
import com.scholar.domain.model.StoryWithStudent
import com.scholar.domain.repo.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryDetailVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val storyRepository: StoryRepository,
) : ViewModel() {

    private val storyId = requireNotNull(savedStateHandle.get<Int>(STORY_ID_KEY))

    private val _story = MutableStateFlow<StoryWithStudent?>(null)
    val story = _story.asStateFlow()

    init {
        getStory()
    }

    private fun getStory() {
        viewModelScope.launch {
            storyRepository.getStoryFromLocal(storyId).collect {
                _story.value = it
            }
        }
    }
}