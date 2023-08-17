package com.scholar.center.ui.materials.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.MaterialReviewAdapter
import com.scholar.center.databinding.FragmentMaterialReviewsBinding
import com.scholar.center.ui.dialogs.RateBottomSheetFragment
import com.scholar.domain.model.Review
import kotlinx.coroutines.launch


class MaterialReviewsFragment(private val viewModel:MaterialDetailVM) : Fragment(R.layout.fragment_material_reviews) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMaterialReviewsBinding.bind(view)

        val reviewsAdapter = MaterialReviewAdapter()

        binding.fab.setOnClickListener {
            val bottomSheetFragment = RateBottomSheetFragment{rate,comment->
                viewModel.rate(rate,comment)
            }
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.materialReviewRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = reviewsAdapter
        }

        lifecycleScope.launch {
            viewModel.material.collect {
                it?.let { materialWithDetail ->
                    reviewsAdapter.setMaterialReviewsList(materialWithDetail.rates)
                }
            }
        }
    }
}

data class TestR(
    override val id: Int,
    override val rating: Float,
    override val comment: String?,
) : Review