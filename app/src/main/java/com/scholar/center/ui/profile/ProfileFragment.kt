package com.scholar.center.ui.profile

import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.scholar.center.R
import com.scholar.center.databinding.FragmentStudentProfileBinding
import com.scholar.center.unit.Constants.BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_student_profile) {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>

    lateinit var binding: FragmentStudentProfileBinding
    private val viewModel: StudentProfileVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentStudentProfileBinding.bind(view)

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
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
                val filePath =
                    requireContext().contentResolver?.openInputStream(uri)?.use { inputStream ->
                        val file = File(requireContext().cacheDir, "temp_file")
                        file.outputStream().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                        file.absolutePath
                    }
                filePath?.let { viewModel.onImageInputChanged(it) }
            }
        }

        binding.profileBrithEditText.setOnClickListener {
            Toast.makeText(requireContext(), "ssssssssss", Toast.LENGTH_SHORT).show()
            openDatePicker()
        }
        binding.profileStudentImageEdit.setOnClickListener {
            if (permissionGranted()) {
                imagePickerLauncher.launch("image/*")
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        }


        binding.profileFullNameEditText.addTextChangedListener {
            viewModel.onNameInputChanged(it.toString())
        }
        binding.profilePhoneEditText.addTextChangedListener {
            viewModel.onPhoneInputChanged(it.toString())
        }

        binding.profileUpdateBtn.setOnClickListener {
            viewModel.onUpdateClick()
        }
        lifecycleScope.launch {
            viewModel.studentState.collect { state ->
                if (state.imageInput.isNotEmpty()) {
                    Glide.with(requireContext())
                        .load(state.imageInput)
                        .error(R.drawable.ic_profile)
                        .into(binding.profileStudentImage)
                } else {
                    Glide.with(requireContext())
                        .load("${BASE_URL}${state.image}")
                        .error(R.drawable.ic_profile)
                        .into(binding.profileStudentImage)
                }

                binding.profileEmailEditText.setText(state.emailInput)
                binding.profileFullNameEditText.setText(state.fullNameInput)
                binding.profileBrithEditText.setText(state.brithInput)
                binding.profilePhoneEditText.setText(state.phoneInput)


                if (state.isInputValid || state.errorMessageInput == null) hideTextError() else showTextError()
                state.errorMessageInput?.let { error ->
                    binding.profileErrorText.text = getString(error)
                }


            }
        }

        lifecycleScope.launch {
            viewModel.message.collectLatest { message ->
                message?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun permissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showTextError() {
        // Fade in
        if (binding.profileErrorText.visibility == View.VISIBLE) return
        val alpha = AlphaAnimation(0f, 1f)
        alpha.duration = 300
        binding.profileErrorText.startAnimation(alpha)
        binding.profileErrorText.visibility = View.VISIBLE
    }

    private fun hideTextError() {
        // Fade out
        if (binding.profileErrorText.visibility == View.GONE) return
        val alpha = AlphaAnimation(1f, 0f)
        alpha.duration = 300
        binding.profileErrorText.startAnimation(alpha)
        binding.profileErrorText.visibility = View.GONE
    }


    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val formattedDate = dateFormat.format(selectedDate.time)

                binding.profileBrithEditText.setText(formattedDate)
                viewModel.onBrithInputChanged(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}