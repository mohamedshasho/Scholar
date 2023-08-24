package com.scholar.center.ui.auth.register

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.scholar.center.R
import com.scholar.center.databinding.FragmentRegisterBinding
import com.scholar.center.unit.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentRegisterBinding.bind(view)
        val mustPopBackStack = arguments?.getBoolean(Constants.MUST_POP_BACK_KEY) ?: false

        val navController = findNavController()

        binding.registerFragmentCreateAccountBtn.setOnClickListener {
            viewModel.onRegisterClick()
        }

        binding.registerFragmentLoginText.setOnClickListener {
            navController.navigate(RegisterFragmentDirections.actionRegisterToLogin(mustPopBackStack))
        }

        binding.registerFragmentFullNameEditText.addTextChangedListener {
            viewModel.onFullNameInputChanged(it.toString())
        }
        binding.registerFragmentEmailEditText.addTextChangedListener {
            viewModel.onEmailInputChanged(it.toString())
        }
        binding.registerFragmentPasswordEditText.addTextChangedListener {
            viewModel.onPasswordInputChanged(it.toString())
        }
        binding.registerFragmentRepeatedPasswordEditText.addTextChangedListener {
            viewModel.onPasswordRepeatedInputChanged(it.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.registerState.collect { state ->
                    val isLoginDestination =
                        navController.currentDestination?.id == R.id.registerFragment //to ensure that navigate call once
                    if (state.isSuccessfullyRegister && isLoginDestination) {
                        if (mustPopBackStack) {
                            navController.popBackStack()
                        } else {
                            navController.navigate(RegisterFragmentDirections.actionRegisterToMain())
                        }
                    }
                    binding.registerFragmentProgressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE

                    if (state.isInputValid || state.errorMessageInput == null) hideTextError() else showTextError()
                    state.errorMessageInput?.let { error ->
                        binding.registerFragmentErrorText.text = getString(error)
                    }
                }
            }
        }
    }

    private fun showTextError() {
        // Fade in
        if (binding.registerFragmentErrorText.visibility == View.VISIBLE) return
        val alpha = AlphaAnimation(0f, 1f)
        alpha.duration = 300
        binding.registerFragmentErrorText.startAnimation(alpha)
        binding.registerFragmentErrorText.visibility = View.VISIBLE
    }

    private fun hideTextError() {
        // Fade out
        if (binding.registerFragmentErrorText.visibility == View.GONE) return
        val alpha = AlphaAnimation(1f, 0f)
        alpha.duration = 300
        binding.registerFragmentErrorText.startAnimation(alpha)
        binding.registerFragmentErrorText.visibility = View.GONE
    }
}
