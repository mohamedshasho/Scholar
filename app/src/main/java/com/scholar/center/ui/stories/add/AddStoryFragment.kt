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
import androidx.navigation.fragment.findNavController
import com.scholar.center.R
import com.scholar.center.databinding.FragmentAddStoryBinding


class AddStoryFragment : Fragment(R.layout.fragment_add_story) {

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
            if(permissionGranted()){
                imagePickerLauncher.launch("image/*")
            }else{
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                imagePickerLauncher.launch("image/*")
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                // Set the selected image to the ImageView
                binding.addStoryToolbarCover.setImageURI(uri)
            }
        }
    }

    private fun permissionGranted() : Boolean{
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }


}