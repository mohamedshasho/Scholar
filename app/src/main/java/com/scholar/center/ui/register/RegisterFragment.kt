package com.scholar.center.ui.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.scholar.center.R
import com.scholar.center.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val binding = FragmentRegisterBinding.bind(view)


        val navController = findNavController()

        binding.registerFragmentCreateAccountBtn.setOnClickListener {
            navController.navigate(RegisterFragmentDirections.actionRegisterToMain())
        }

        binding.registerFragmentLoginText.setOnClickListener {
            navController.navigate(RegisterFragmentDirections.actionRegisterToLogin())
        }

    }
}
