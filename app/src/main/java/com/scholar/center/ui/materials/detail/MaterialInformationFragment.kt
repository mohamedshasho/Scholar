package com.scholar.center.ui.materials.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.scholar.center.R
import com.scholar.center.databinding.FragmentMaterialInformationBinding
import kotlinx.coroutines.launch

class MaterialInformationFragment(private val viewModel: MaterialDetailVM) :
    Fragment(R.layout.fragment_material_information) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMaterialInformationBinding.bind(view)

        lifecycleScope.launch {
            viewModel.material.collect {
                it?.let { materialWithDetail ->
                    binding.materialInformationSubject.text = materialWithDetail.subject
                    binding.materialInformationTitle.text = materialWithDetail.material.title
                    binding.materialInformationDescription.text =
                        materialWithDetail.material.description
                }
            }
        }

    }
}