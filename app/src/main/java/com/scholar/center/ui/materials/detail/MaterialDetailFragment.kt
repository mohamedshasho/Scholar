package com.scholar.center.ui.materials.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.scholar.center.R
import com.scholar.center.adapter.TeacherPagerAdapter
import com.scholar.center.databinding.FragmentMaterialDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MaterialDetailFragment : Fragment(R.layout.fragment_material_detail) {
    private val viewModel: MaterialDetailVM by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMaterialDetailBinding.bind(view)

        binding.materialToolbarBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        val tabLayout = binding.materialTabLayout
        val viewPager = binding.materialViewPager

        val fragments = listOf(
            MaterialInformationFragment(), MaterialReviewsFragment(),
        )
        val materialAdapter = TeacherPagerAdapter(
            fragments = fragments,
            fragmentActivity = requireActivity()
        )
        viewPager.adapter = materialAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.information)
                1 -> getString(R.string.reviews)
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }.attach()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.material.collect {
                    it?.let { material ->
                        binding.materialName.text = material.title
                        Log.d("material.price", "onViewCreated: ${material.price}")
//                        binding.materialDescription.text = material.description
                        if (material.price == null || material.price == 0) {
                            binding.materialPriceLayout.visibility = View.GONE
                            binding.materialPriceButton.text = getText(R.string.view)
                        } else {
                            binding.materialPriceValue.text =
                                getString(R.string.syr, material.price)
                        }


                        binding.materialPriceButton.setOnClickListener {
                            if (material.price == null || material.price == 0) {
                                // navigate to show
                            } else {
                                // purchase
                            }
                        }
                    }
                }
            }
        }

    }
}