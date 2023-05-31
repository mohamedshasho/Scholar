package com.scholar.center.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.scholar.center.R
import com.scholar.center.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val binding = FragmentLoginBinding.bind(view)


        val navController = findNavController()

        binding.loginFragmentLoginBtn.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginToMain())
        }

        binding.loginFragmentNoAccount.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginToRegister())
        }

    }

}