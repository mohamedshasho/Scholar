package com.scholar.center.ui.stories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.StoryAdapter
import com.scholar.center.adapter.StoryComparator
import com.scholar.center.databinding.FragmentStoriesBinding
import com.scholar.center.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoriesFragment : Fragment(R.layout.fragment_stories) {

    private val viewModel: StoriesVM by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentStoriesBinding.bind(view)

        val navController = activity?.findNavController(R.id.root_nav_host_fragment)

        val storyAdapter = StoryAdapter(StoryComparator) { id ->
            navController?.navigate(MainFragmentDirections.actionMainToStoryDetail(storyId = id))
        }
        lifecycleScope.launch {
            viewModel.stories.collectLatest { pagingData ->
                storyAdapter.submitData(pagingData)
            }
        }

        binding.storiesFragmentRecyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = storyAdapter
        }

        binding.storiesFragmentAdd.setOnClickListener {
            navController?.navigate(MainFragmentDirections.actionMainToAddStory())
        }

    }
}