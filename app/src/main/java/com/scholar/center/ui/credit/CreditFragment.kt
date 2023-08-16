package com.scholar.center.ui.credit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.scholar.center.R
import com.scholar.center.databinding.FragmentCreditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditFragment :Fragment(R.layout.fragment_credit) {
    lateinit var binding  :FragmentCreditBinding
    private val viewModel : CreditVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCreditBinding.bind(view)



    }
}