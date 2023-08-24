package com.scholar.center.ui.materials


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.scholar.center.R
import com.scholar.center.databinding.FragmentVideoBinding
import com.scholar.center.unit.Constants.BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class VideoFragment : Fragment(R.layout.fragment_video) {
    private val viewModel : MaterialContentVM by viewModels()
    private lateinit var binding: FragmentVideoBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentVideoBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.material.collect { it ->
                    val material = it?.material
                    material?.content?.let { link ->
                        val url = "${BASE_URL}$link"
                        binding.andExoPlayerView.setSource(url)
                    }
                }
            }
        }
    }

}