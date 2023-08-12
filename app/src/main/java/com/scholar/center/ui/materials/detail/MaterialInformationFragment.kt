package com.scholar.center.ui.materials.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.scholar.center.R
import com.scholar.center.databinding.FragmentMaterialInformationBinding

class MaterialInformationFragment : Fragment(R.layout.fragment_material_information) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMaterialInformationBinding.bind(view)

        binding.materialInformationDescription.text = " نوطة رياضيات\n نوطة رياضيات للصف الاول شاملة "
    }
}