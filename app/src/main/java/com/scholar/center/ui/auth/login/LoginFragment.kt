package com.scholar.center.ui.auth.login

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
import com.scholar.center.databinding.FragmentLoginBinding
import com.scholar.center.unit.Constants.MUST_POP_BACK_KEY
import com.scholar.center.unit.getThemeColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.google.android.material.R.attr as theme

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginVM by viewModels()
    private lateinit var binding: FragmentLoginBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentLoginBinding.bind(view)

        val mustPopBackStack = arguments?.getBoolean(MUST_POP_BACK_KEY) ?: false

        val text = getString(R.string.welcome)
        val colorPrimaryContainer = requireContext().getThemeColor(
            theme.colorPrimary
        )
        val spannable = SpannableString(text)
        spannable.setSpan(
            ForegroundColorSpan(colorPrimaryContainer),
            0, text.split(" ").first().length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.loginFragmentWelcomeText.text = spannable


        val navController = findNavController()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.loginState.collect { state ->
                    val isLoginDestination =
                        navController.currentDestination?.id == R.id.loginFragment //to ensure that navigate call once
                    if (state.isSuccessfullyLogin && isLoginDestination) {
                        if (mustPopBackStack) {
                            navController.popBackStack()
                        } else {
                            navController.navigate(LoginFragmentDirections.actionLoginToMain())
                        }
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
            navController.navigate(LoginFragmentDirections.actionLoginToRegister(mustPopBackStack))
        }
        binding.loginFragmentSkipLayout.setOnClickListener {
            if (mustPopBackStack) {
                navController.popBackStack()
            } else {
                navController.navigate(LoginFragmentDirections.actionLoginToMain())
            }
        }

        binding.loginFragmentContactAsText.setOnClickListener {
                navController.navigate(LoginFragmentDirections.actionLoginToContact())
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