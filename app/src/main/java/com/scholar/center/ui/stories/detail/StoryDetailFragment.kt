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
                it?.let { story ->
                    binding.storyItemDateText.text = story.datePublication
                    binding.storyDetailStudentName.text = story.studentName
                    binding.storyDetailDescription.text = story.description
                    binding.storyDetailTitle.text = story.title


                    Glide.with(requireContext())
                        .load("${BASE_URL}${story.studentProfile}")
                        .placeholder(R.drawable.ic_person)
                        .into(binding.storyDetailStudentImage)

                    Glide.with(requireContext())
                        .load("${BASE_URL}${story.image}")
                        .placeholder(R.drawable.ic_story)
                        .into(binding.storyDetailCover)

                }

            }
        }


    }

}