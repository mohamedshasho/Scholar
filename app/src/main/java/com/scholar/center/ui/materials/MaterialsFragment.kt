package com.scholar.center.ui.materials


import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
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
import com.scholar.center.databinding.FragmentMaterialsBinding
import com.scholar.center.model.UiState
import com.scholar.center.ui.dialogs.MaterialFilterDialog
import com.scholar.center.unit.Constants.SEARCH_FOCUS_KEY
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
        menuInflater.inflate(R.menu.search_bar, menu)
        val search = menu.findItem(R.id.action_search).actionView as SearchView
        search.queryHint = getString(R.string.enter_text)



        val focusOnSearch = arguments?.getBoolean(SEARCH_FOCUS_KEY) ?: false

        if (focusOnSearch) {
            search.onActionViewExpanded()
            search.requestFocus()
            val imm = activity?.getSystemService<InputMethodManager>()
            imm?.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT)
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                Toast.makeText(requireContext(), newText, Toast.LENGTH_SHORT).show()
                closeKeyboard()
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
                    viewModel.categories.value,
                    viewModel.stages.value,
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


        val subjectsAdapter = MaterialAdapter(
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
            adapter = subjectsAdapter
        }

//        binding.subjectsToolbarBackButton.setOnClickListener {
//            navController.popBackStack()
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        is UiState.Success -> {
                            binding.materialsProgressBar.visibility = View.GONE
                            state.data?.let { list ->
                                subjectsAdapter.setList(list)
                            }
                        }
                        is UiState.Error -> {}
                        else -> {
                            binding.materialsProgressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun closeKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}