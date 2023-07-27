package com.scholar.center.ui.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.scholar.domain.repo.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoryVM  @Inject constructor(
    private val storyRepository: StoryRepository
) : ViewModel(){


    val stories = storyRepository.getStoriesPagination()
        .cachedIn(viewModelScope)


}