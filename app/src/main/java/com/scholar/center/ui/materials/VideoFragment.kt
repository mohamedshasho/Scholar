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

    private val link =
        "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1"
    private val localLink = "http://192.168.1.101:8000/media/1/promo.mp4"
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

    /*
    private fun playVideoWithVideoView(){
        val uri = Uri.parse(localLink)
        val mediaController = MediaController(requireContext())
//        mediaController.setAnchorView(binding.videoView)
//        mediaController.setMediaPlayer(binding.videoView)
//        binding.videoView.setMediaController(mediaController)
//        binding.videoView.setVideoURI(uri)
//        binding.progressBar.visibility = View.VISIBLE
//        binding.videoView.setOnPreparedListener {mp: MediaPlayer->
//            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
//            binding.progressBar.visibility = View.GONE
//            mp.setScreenOnWhilePlaying(false)
//
//        }
//        binding.videoView.start()
    }
*/

}