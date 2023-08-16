package com.scholar.center.ui.contact

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.scholar.center.R
import com.scholar.center.databinding.FragmentContactAsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ContactFragment : Fragment(R.layout.fragment_contact_as) {
    lateinit var binding: FragmentContactAsBinding

    private val viewModel : ContactVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentContactAsBinding.bind(view)



        lifecycleScope.launch {
            viewModel.message.collectLatest { message ->
                message?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        val navController = findNavController()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.contactState.collect { state ->
                    val isLoginDestination =
                        navController.currentDestination?.id == R.id.contactFragment //to ensure that navigate call once
                    if (state.isSuccessfullySend && isLoginDestination) {
                            navController.popBackStack()
                    }
                    binding.contactFragmentProgressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE

                    if (state.isInputValid || state.errorMessageInput == null) hideTextError() else showTextError()
                    state.errorMessageInput?.let { error ->
                        binding.contactFragmentErrorText.text = getString(error)
                    }
                }
            }
        }

        binding.contactNameEditText.addTextChangedListener {
            viewModel.onNameInputChanged(it.toString())
        }
        binding.contactPhoneEditText.addTextChangedListener {
            viewModel.onPhoneInputChanged(it.toString())
        }
        binding.contactEmailEditText.addTextChangedListener {
            viewModel.onEmailInputChanged(it.toString())
        }
        binding.contactSubjectEditText.addTextChangedListener {
            viewModel.onSubjectInputChanged(it.toString())
        }
        binding.addContactSend.setOnClickListener {
            viewModel.onSendClick()
        }
    }

    private fun showTextError() {
        // Fade in
        if (binding.contactFragmentErrorText.visibility == View.VISIBLE) return
        val alpha = AlphaAnimation(0f, 1f)
        alpha.duration = 300
        binding.contactFragmentErrorText.startAnimation(alpha)
        binding.contactFragmentErrorText.visibility = View.VISIBLE
    }

    private fun hideTextError() {
        // Fade out
        if (binding.contactFragmentErrorText.visibility == View.GONE) return
        val alpha = AlphaAnimation(1f, 0f)
        alpha.duration = 300
        binding.contactFragmentErrorText.startAnimation(alpha)
        binding.contactFragmentErrorText.visibility = View.GONE
    }
}