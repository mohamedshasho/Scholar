package com.scholar.center.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
) : DialogFragment(R.layout.dialog_material_filter) {

    private lateinit var binding: DialogMaterialFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = DialogMaterialFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.filterCancel.setOnClickListener { dismiss() }
        binding.filterOk.setOnClickListener {
            onClickOK
            dismiss()
        }


        setupCategories()
        setupStages()
        setupClassRooms()
        setupClassSubjects()




        binding.filterSpinnerStages.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    if (position == 0) return

                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setupClassSubjects() {
        val subjectsData = mutableListOf<String>()
        subjectsData.add(0, getString(R.string.select_subject))
        val subjectsAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, subjectsData)
        subjectsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.filterSpinnerSubjects.adapter = subjectsAdapter
        binding.filterSpinnerSubjects.onItemSelectedListener= adapterSelectedListener { position->

        }
    }

    private fun setupClassRooms() {
        val classRoomsData = mutableListOf<String>()
        classRoomsData.add(0, getString(R.string.select_class))
        val classRoomsAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, classRoomsData)
        classRoomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.filterSpinnerClassRooms.adapter = classRoomsAdapter
        binding.filterSpinnerClassRooms.onItemSelectedListener= adapterSelectedListener { position->

        }
    }


    private fun setupStages() {
        val stagesData = stages.map { it.name }.toMutableList()
        stagesData.add(0, getString(R.string.select_stage))
        val stageAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, stagesData)
        stageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.filterSpinnerStages.adapter = stageAdapter
        binding.filterSpinnerStages.onItemSelectedListener= adapterSelectedListener { position->
            Toast.makeText(requireContext(), stagesData[position - 1], Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun setupCategories() {
        val categoriesData = categories.map { it.name }.toMutableList()
        categoriesData.add(0, getString(R.string.select_category))
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categoriesData
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.filterSpinnerCategories.adapter = adapter
        binding.filterSpinnerCategories.onItemSelectedListener = adapterSelectedListener { position ->
            Toast.makeText(requireContext(), categories[position - 1].name, Toast.LENGTH_SHORT)
                .show()
        }

    }


    private fun adapterSelectedListener(selectedPosition: (Int) -> Unit) =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                if (position == 0) return // for label
                selectedPosition(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

}