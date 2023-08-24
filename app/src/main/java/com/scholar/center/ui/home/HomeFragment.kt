package com.scholar.center.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.CardClassITemAdapter
import com.scholar.center.adapter.CategoryAdapter
import com.scholar.center.adapter.MaterialAdapter
import com.scholar.center.databinding.FragmentHomeBinding
import com.scholar.center.ui.main.MainFragmentDirections
import com.scholar.domain.model.Stage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeVM by viewModels()
    private val navController by lazy { activity?.findNavController(R.id.root_nav_host_fragment) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentHomeBinding.bind(view)) {
            val categoriesShimmer = homeMaterialsSubjectsShimmerLayout
            val materialsShimmer =homeMaterialsShimmerLayout

            val materialAdapter = MaterialAdapter(
                navigateToTeacher = { teacherID ->
                    navController?.navigate(MainFragmentDirections.actionMainToTeacher(teacherId = teacherID))
                },
                navigateToDetails = { id ->
                    navController?.navigate(MainFragmentDirections.actionMainToMaterial(id))
                }
            )
            val categoryAdapter = CategoryAdapter { categoryId ->
                navController?.navigate(MainFragmentDirections.actionHomeToMaterials(categoryId = categoryId))
            }

            homeMaterialsSubjectsRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = categoryAdapter
            }

            val stagesAdapter = CardClassITemAdapter<Stage> { id ->
                navController?.navigate(MainFragmentDirections.actionMainToClasses(id))
            }

            homeStageRecyclerView.run {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = stagesAdapter
            }

            lifecycleScope.launch {
                viewModel.materials.collect { materials ->
                    materialAdapter.setList(materials)
                }
            }

            lifecycleScope.launch {
                viewModel.categories.collect { categories ->
                    categoriesShimmer.stopShimmer()
                    categoriesShimmer.visibility = View.GONE
                    categoryAdapter.setMaterialSubjectList(categories)
                }
            }
            lifecycleScope.launch {
                viewModel.loading.collect { loading ->
                    if (loading) {
                        materialsShimmer.startShimmer()
                    } else {
                        materialsShimmer.stopShimmer()
                        materialsShimmer.visibility = View.GONE
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.stages.collect { stages ->
                        stagesAdapter.setData(stages)
                    }
                }
            }

            homeSubjectsRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = materialAdapter
            }


            homeMaterialsSeeAllText.setOnClickListener {
                navController?.navigate(MainFragmentDirections.actionHomeToMaterials())
            }

            searchViewLayout.setOnClickListener {
                navController?.navigate(MainFragmentDirections.actionHomeToMaterials(searchFocus = true))
            }
        }
    }

}