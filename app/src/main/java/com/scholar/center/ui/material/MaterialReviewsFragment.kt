package com.scholar.center.ui.material

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scholar.center.R
import com.scholar.center.adapter.MaterialReviewAdapter
import com.scholar.center.databinding.FragmentMaterialReviewsBinding
import com.scholar.domain.model.Review


class MaterialReviewsFragment : Fragment(R.layout.fragment_material_reviews) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMaterialReviewsBinding.bind(view)

        val reviewsAdapter = MaterialReviewAdapter()

        binding.materialReviewRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = reviewsAdapter
        }

        val list = listOf(
            TestR(1, 3.5f, "comment 1"),
            TestR(1, 5f, "comment 2"),
            TestR(1, 2f, "comment 3")
        )
        reviewsAdapter.setMaterialReviewsList(list)

    }
}

data class TestR(
    override val id: Int,
    override val rating: Float,
    override val comment: String?,
) : Review