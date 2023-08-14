package com.scholar.center.ui.materials


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.MaterialAdapter
import com.scholar.center.adapter.MaterialPagingAdapter
import com.scholar.center.adapter.MaterialsComparator
import com.scholar.center.databinding.FragmentMaterialsBinding
import com.scholar.center.model.UiState
import com.scholar.center.ui.dialogs.MaterialFilterDialog
import com.scholar.center.unit.Constants.SEARCH_FOCUS_KEY
import com.scholar.center.unit.closeKeyboard
import com.scholar.center.unit.openKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MaterialsFragment : Fragment(R.layout.fragment_materials), MenuProvider {

    private val viewModel: MaterialsVM by viewModels()


    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentMaterialsBinding.inflate(inflater)
        val toolbar = binding.materialToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.materials_search_bar, menu)
        val search = menu.findItem(R.id.action_search).actionView as SearchView
        search.queryHint = getString(R.string.enter_text)


        val focusOnSearch = arguments?.getBoolean(SEARCH_FOCUS_KEY) ?: false

        if (focusOnSearch) {
            search.onActionViewExpanded()
            search.requestFocus()
            search.openKeyboard(activity)
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                viewModel.search(newText)
                view?.closeKeyboard(activity)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        // Handle the menu selection
        return when (menuItem.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                true
            }
            R.id.action_search -> {
                true
            }
            R.id.action_filter -> {
                val filterDialog = MaterialFilterDialog(
                    viewModel = viewModel,
                ) {

                }
                filterDialog.show(parentFragmentManager, "FilterDialog")
                true
            }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMaterialsBinding.bind(view)


        val materialsAdapter = MaterialPagingAdapter(
            diffCallback = MaterialsComparator,
            navigateToTeacher = { teacherID ->
                navController.navigate(
                    MaterialsFragmentDirections.actionMaterialsToTeacher(
                        teacherId = teacherID
                    )
                )
            },
            navigateToDetails = { id ->
                navController.navigate(
                    MaterialsFragmentDirections.actionMaterialsToMaterialDetail(
                        materialId = id
                    )
                )
            }
        )

        binding.subjectsRecyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = materialsAdapter
        }

//        binding.subjectsToolbarBackButton.setOnClickListener {
//            navController.popBackStack()
//        }
        lifecycleScope.launch {
            viewModel.loading.collectLatest { loading ->
                binding.materialsProgressBar.visibility = if (loading) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.materials.collectLatest { materials ->
                    materialsAdapter.submitData(materials)
                }
            }
        }
    }


}