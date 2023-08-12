package com.scholar.center.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.scholar.center.R
import com.scholar.center.databinding.DialogMaterialFilterBinding
import com.scholar.center.ui.materials.MaterialsVM
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MaterialFilterDialog(
    private val viewModel: MaterialsVM,
    private val onClickOK: () -> Unit,
) : DialogFragment(R.layout.dialog_material_filter) {

    private lateinit var binding: DialogMaterialFilterBinding

    private lateinit var classRoomsAdapter: ArrayAdapter<String>
    private lateinit var subjectsAdapter: ArrayAdapter<String>
    private lateinit var stageAdapter: ArrayAdapter<String>
    private lateinit var categoriesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classRoomsAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        subjectsAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        stageAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        categoriesAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

    }

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
            viewModel.selectedClassRoom = binding.filterSpinnerClassRooms.selectedItemPosition
            viewModel.selectedCategory = binding.filterSpinnerCategories.selectedItemPosition
            viewModel.selectedStage = binding.filterSpinnerStages.selectedItemPosition
            viewModel.selectedSubject = binding.filterSpinnerSubjects.selectedItemPosition
            dismiss()
        }
        binding.filterSpinnerClassRooms.isEnabled = false
        binding.filterSpinnerClassRooms.adapter = classRoomsAdapter
        binding.filterSpinnerClassRooms.onItemSelectedListener =
            adapterSelectedListener { position ->
            }
        addClassRooms()
        binding.filterSpinnerSubjects.adapter = subjectsAdapter
        binding.filterSpinnerSubjects.onItemSelectedListener = adapterSelectedListener { position ->
        }
        binding.filterSpinnerCategories.adapter = categoriesAdapter
        binding.filterSpinnerCategories.onItemSelectedListener =
            adapterSelectedListener { position ->
            }
        binding.filterSpinnerStages.adapter = stageAdapter
        binding.filterSpinnerStages.onItemSelectedListener =
            adapterSelectedListener(skipThisPosition = -1) { position ->
                when (position) {
                    0 -> {
                        binding.filterSpinnerClassRooms.isEnabled = false
                    }
                    1 -> {
                        binding.filterSpinnerClassRooms.isEnabled = true
                        val classrooms = viewModel.classrooms.value
                        addClassRooms(classrooms.map { it.name })
                    }
                    else -> {
                        binding.filterSpinnerClassRooms.isEnabled = true
                        val classrooms = viewModel.classrooms.value.filter { it.id < 4 }
                        addClassRooms(classrooms.map { it.name })
                    }
                }
                if (viewModel.selectedStage == 0 || viewModel.selectedStage != position) {
                    binding.filterSpinnerClassRooms.setSelection(0)
                } else {
                    binding.filterSpinnerClassRooms.setSelection(viewModel.selectedClassRoom)
                }
            }

        lifecycleScope.launch {
            launch {
                viewModel.stages.collectLatest { stages ->
                    val stagesData = stages.map { it.name }.toMutableList()
                    stagesData.add(0, getString(R.string.select_stage))
                    stageAdapter.clear()
                    stageAdapter.addAll(stagesData)
                    stageAdapter.notifyDataSetChanged()
                    binding.filterSpinnerStages.setSelection(viewModel.selectedStage)
                }
            }
            launch {
                viewModel.categories.collectLatest { categories ->
                    val categoriesData = categories.map { it.name }.toMutableList()
                    categoriesData.add(0, getString(R.string.select_category))
                    categoriesAdapter.clear()
                    categoriesAdapter.addAll(categoriesData)
                    categoriesAdapter.notifyDataSetChanged()
                    binding.filterSpinnerCategories.setSelection(viewModel.selectedCategory)
                }
            }
            launch {
                viewModel.subjects.collectLatest { subjects ->
                    if (subjects.isNotEmpty()) {
                        val subjectsData = subjects.map { it.name }.toMutableList()
                        subjectsData.add(0, getString(R.string.select_subject))
                        subjectsAdapter.clear()
                        subjectsAdapter.addAll(subjectsData)
                        subjectsAdapter.notifyDataSetChanged()
                        binding.filterSpinnerSubjects.setSelection(viewModel.selectedSubject)
                    }
                }
            }
        }
    }

    private fun addClassRooms(data: List<String> = emptyList()) {
        val mutableData = data.toMutableList()
        mutableData.add(0, getString(R.string.select_class))
        classRoomsAdapter.clear()
        classRoomsAdapter.addAll(mutableData)
        classRoomsAdapter.notifyDataSetChanged()
    }

    private fun adapterSelectedListener(
        skipThisPosition: Int = 0,
        selectedPosition: (Int) -> Unit,
    ) =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                if (position == skipThisPosition) return // for label
                selectedPosition(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

}