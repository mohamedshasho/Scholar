package com.scholar.center.ui.story

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.StoryAdapter
import com.scholar.center.databinding.FragmentStoriesBinding

class StoryFragment : Fragment(R.layout.fragment_stories) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentStoriesBinding.bind(view)


        val storyAdapter = StoryAdapter()

        binding.storiesFragmentRecyclerView.run {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = storyAdapter
        }

    }
}