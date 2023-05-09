package com.scholar.center.ui.story

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.StoryAdapter
import com.scholar.center.databinding.FragmentStoriesBinding
import com.scholar.data.model.MaterialSubject

class StoryFragment : Fragment(R.layout.fragment_stories) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentStoriesBinding.bind(view)

        val items = listOf(
            MaterialSubject(name = "name1", image = ""),
            MaterialSubject(name = "name2", image = ""),
            MaterialSubject(name = "name3", image = ""),
            MaterialSubject(name = "name3", image = ""),
            MaterialSubject(name = "name3", image = ""),
            MaterialSubject(name = "name3", image = ""),
            MaterialSubject(name = "name3", image = ""),
        )
        val storyAdapter = StoryAdapter(items)

        binding.storiesFragmentRecyclerView.run {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = storyAdapter
        }

    }
}