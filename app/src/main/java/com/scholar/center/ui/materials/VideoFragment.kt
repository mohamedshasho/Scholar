package com.scholar.center.ui.materials

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.MimeTypes
import com.scholar.center.R
import com.scholar.center.databinding.FragmentVideoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : Fragment(R.layout.fragment_video) {
//    private val viewModel : MaterialContentVM by viewModels()

    private var player: ExoPlayer? = null
    private val isPlaying get() = player?.isPlaying ?: false
    private lateinit var binding: FragmentVideoBinding

    private val link = "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentVideoBinding.bind(view)

        val v = "https://4422-146-70-174-67.ngrok-free.app/media/1/cc.mp4"


        // Uri object to refer the
        // resource from the videoUrl
        // Uri object to refer the
        // resource from the videoUrl
        val uri = Uri.parse(v)

        // sets the resource from the
        // videoUrl to the videoView

        // sets the resource from the
        // videoUrl to the videoView
        binding.videoView.setVideoURI(uri)

        // creating object of
        // media controller class

        // creating object of
        // media controller class
        val mediaController = MediaController(requireContext())

        // sets the anchor view
        // anchor view for the videoView

        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(binding.videoView)

        // sets the media player to the videoView

        // sets the media player to the videoView
        mediaController.setMediaPlayer(binding.videoView)

        // sets the media controller to the videoView

        // sets the media controller to the videoView
        binding.videoView.setMediaController(mediaController)

        binding.videoView.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        // starts the video

        // starts the video
        binding.videoView.start()

//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.material.collect { it ->
//                    val material = it?.material
//                    material?.content?.let { link ->
//
//                    }
//                }
//            }
//        }
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext()) // <- context
            .build()

        // create a media item.
        val mediaItem = MediaItem.Builder()
            .setUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
            .setMimeType(MimeTypes.APPLICATION_MP4)
            .build()

        // Create a media source and pass the media item
        val mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSource.Factory(requireContext()) // <- context
        )
            .createMediaSource(mediaItem)

        // Finally assign this media source to the player
        player!!.apply {
            setMediaSource(mediaSource)
            playWhenReady = true // start playing when the exoplayer has setup
            seekTo(0, 0L) // Start from the beginning
            prepare() // Change the state from idle.
        }.also {
            // Do not forget to attach the player to the view

        }
    }
}