package com.scholar.center.ui.stories.add

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scholar.center.R
import com.scholar.center.databinding.FragmentAddStoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class AddStoryFragment : Fragment(R.layout.fragment_add_story) {

    private val viewModel: AddStoryVM by viewModels()

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val PERMISSION_REQUEST = 2
    }

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentAddStoryBinding.bind(view)
        binding.addStoryToolbarBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addStoryToolbarCoverLayout.setOnClickListener {
            if (permissionGranted()) {
                imagePickerLauncher.launch("image/*")
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        }

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    imagePickerLauncher.launch("image/*")
                } else {
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }

        lifecycleScope.launch {
            viewModel.selectedUri.collectLatest { uri ->
                binding.addStoryToolbarCover.setImageURI(uri)
            }
        }

        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                val filePath =
                    requireContext().contentResolver?.openInputStream(uri)?.use { inputStream ->
                        val file = File(requireContext().cacheDir, "temp_file")
                        file.outputStream().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                        file.absolutePath
                    }
                viewModel.setUri(uri, filePath)
            }
        }

        binding.addStorySend.setOnClickListener {
            viewModel.addStory(
                binding.addStoryTitle.text.toString(),
                binding.addStoryDescription.text.toString(),
                studentId = 1
            )
        }
    }

    private fun permissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }


}