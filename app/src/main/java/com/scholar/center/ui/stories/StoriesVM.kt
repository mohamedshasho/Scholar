package com.scholar.center.ui.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.scholar.domain.repo.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoriesVM  @Inject constructor(
    storyRepository: StoryRepository
) : ViewModel(){

    val stories = storyRepository.getStoriesPagination()
        .cachedIn(viewModelScope)
}