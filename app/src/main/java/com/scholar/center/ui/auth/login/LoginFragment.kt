package com.scholar.center.ui.auth.login

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
import com.scholar.center.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginVM by viewModels()
    private lateinit var binding: FragmentLoginBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentLoginBinding.bind(view)


        val navController = findNavController()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.loginState.collect { state ->
                    val isLoginDestination =
                        navController.currentDestination?.id == R.id.loginFragment //to ensure that navigate call once
                    if (state.isSuccessfullyLogin && isLoginDestination) {
                        navController.navigate(LoginFragmentDirections.actionLoginToMain())
                    }
                    binding.loginFragmentProgressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE

                    if (state.isInputValid || state.errorMessageInput == null) hideTextError() else showTextError()
                    state.errorMessageInput?.let { error ->
                        binding.loginFragmentErrorText.text = getString(error)
                    }

                }
            }
        }

        binding.loginFragmentLoginBtn.setOnClickListener {
            viewModel.onLoginClick()
        }

        binding.loginFragmentUsernameEditText.addTextChangedListener {
            viewModel.onEmailInputChanged(it.toString())
        }
        binding.loginFragmentPasswordEditText.addTextChangedListener {
            viewModel.onPasswordInputChanged(it.toString())
        }
        binding.loginFragmentNoAccount.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginToRegister())
        }
        binding.loginFragmentSkipLayout.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginToMain())
        }

    }

    private fun showTextError() {
        // Fade in
        if (binding.loginFragmentErrorText.visibility == View.VISIBLE) return
        val alpha = AlphaAnimation(0f, 1f)
        alpha.duration = 300
        binding.loginFragmentErrorText.startAnimation(alpha)
        binding.loginFragmentErrorText.visibility = View.VISIBLE
    }

    private fun hideTextError() {
        // Fade out
        if (binding.loginFragmentErrorText.visibility == View.GONE) return
        val alpha = AlphaAnimation(1f, 0f)
        alpha.duration = 300
        binding.loginFragmentErrorText.startAnimation(alpha)
        binding.loginFragmentErrorText.visibility = View.GONE
    }

}