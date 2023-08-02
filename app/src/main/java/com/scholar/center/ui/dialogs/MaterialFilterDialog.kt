package com.scholar.center.ui.dialogs

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.scholar.center.R
import com.scholar.center.databinding.DialogMaterialFilterBinding
import com.scholar.domain.model.Category
import com.scholar.domain.model.Stage


class MaterialFilterDialog(
    private val categories: List<Category>,
    private val stages: List<Stage>,
    private val onClickOK: () -> Unit,
) :
    DialogFragment(R.layout.dialog_material_filter) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DialogMaterialFilterBinding.bind(view)

        binding.filterCancel.setOnClickListener { dismiss() }
        binding.filterOk.setOnClickListener {
            onClickOK
            dismiss()
        }


        val categoriesData = categories.map { it.name }.toMutableList()
        categoriesData.add(0, getString(R.string.select_category))
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categoriesData
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.filterSpinnerCategories.adapter = adapter

        val stagesData = stages.map { it.name }.toMutableList()
        stagesData.add(0,getString(R.string.select_stage))
        val stageAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, stagesData)
        stageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.filterSpinnerStages.adapter = stageAdapter


        binding.filterSpinnerCategories.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long, ) {
                    if(position==0) return
                    Toast.makeText(requireContext(), categories[position-1].name, Toast.LENGTH_SHORT).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.filterSpinnerStages.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long, ) {
                    if(position==0) return
                    Toast.makeText(requireContext(), stagesData[position-1], Toast.LENGTH_SHORT).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }


}