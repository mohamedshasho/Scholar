package com.scholar.center.ui.stories.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.scholar.center.R
import com.scholar.center.databinding.FragmentStoryDetailBinding
import com.scholar.center.unit.Constants.BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint

class StoryDetailFragment : Fragment(R.layout.fragment_story_detail) {
    private val viewModel: StoryDetailVM by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentStoryDetailBinding.bind(view)

        binding.storyDetailBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            viewModel.story.collectLatest {
                it?.let { storyWithStudent ->
                    binding.storyItemDateText.text = storyWithStudent.story.datePublication
                    binding.storyDetailStudentName.text = storyWithStudent.student.fullName
                    binding.storyDetailDescription.text = storyWithStudent.story.description
                    binding.storyDetailTitle.text = storyWithStudent.story.title


                    Glide.with(requireContext())
                        .load("${BASE_URL}${storyWithStudent.student.image}")
                        .placeholder(R.drawable.ic_person)
                        .error(R.drawable.ic_person)
                        .into(binding.storyDetailStudentImage)

                    Glide.with(requireContext())
                        .load("${BASE_URL}${storyWithStudent.story.image}")
                        .placeholder(R.drawable.story_placeholder)
                        .error(R.drawable.story_placeholder)
                        .into(binding.storyDetailCover)

                }

            }
        }


    }

}