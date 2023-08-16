package com.scholar.center.ui.credit

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.scholar.center.R
import com.scholar.center.databinding.FragmentCreditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class CreditFragment : Fragment(R.layout.fragment_credit) {
    lateinit var binding: FragmentCreditBinding
    private val viewModel: CreditVM by viewModels()

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private lateinit var methodAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        methodAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreditBinding.bind(view)

        binding.creditAmountEditText.addTextChangedListener {
            if (it.toString().isNotEmpty())
                viewModel.onAmountInputChanged(it.toString().toInt())
        }
        binding.creditDescriptionEditText.addTextChangedListener {
            viewModel.onDescriptionInputChanged(it.toString())
        }

        binding.addContactSend.setOnClickListener {
            viewModel.onSendClick()
        }

        binding.creditSpinnerMethod.adapter = methodAdapter
        addMethod()
        binding.creditSpinnerMethod.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    viewModel.onMethodInputChanged(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }


        binding.addImagePaymentLayout.setOnClickListener {
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
            viewModel.message.collectLatest { message ->
                message?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
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
                filePath?.let { viewModel.onLinkInputChanged(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.creditState.collect { state ->
                    binding.creditProgressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE

                    if (state.isInputValid || state.errorMessageInput == null)
                        hideTextError() else showTextError()
                    state.errorMessageInput?.let { error ->
                        binding.creditErrorText.text = getString(error)
                    }
                    if (state.linkInput.isNotEmpty()) {
                        binding.addImagePaymentImageview.setImageResource(R.drawable.check_mark)
                    }
                    val hasErrorMessage = state.errorMessageLoginProcess.isNullOrEmpty()
                    if (!hasErrorMessage) {
                        binding.creditErrorText.text = state.errorMessageLoginProcess
                        showTextError()
                    }

                    if(state.isSuccessfullySend){
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun addMethod(data: List<String> = emptyList()) {
        val mutableData = data.toMutableList()
        mutableData.add(getString(R.string.select_method))
        mutableData.add("شركة الهرم")
        mutableData.add("بنك البركة")
        mutableData.add("بنك بيبلوس")
        mutableData.add("بنك بيمو السعودي الفرنسي")
        mutableData.add("MTN cash")
        mutableData.add("SYRIATEL cash")
        methodAdapter.clear()
        methodAdapter.addAll(mutableData)
        methodAdapter.notifyDataSetChanged()
    }

    private fun permissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showTextError() {
        // Fade in
        if (binding.creditErrorText.visibility == View.VISIBLE) return
        val alpha = AlphaAnimation(0f, 1f)
        alpha.duration = 300
        binding.creditErrorText.startAnimation(alpha)
        binding.creditErrorText.visibility = View.VISIBLE
    }

    private fun hideTextError() {
        // Fade out
        if (binding.creditErrorText.visibility == View.GONE) return
        val alpha = AlphaAnimation(1f, 0f)
        alpha.duration = 300
        binding.creditErrorText.startAnimation(alpha)
        binding.creditErrorText.visibility = View.GONE
    }
}