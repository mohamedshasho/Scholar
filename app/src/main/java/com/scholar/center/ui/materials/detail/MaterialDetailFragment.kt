package com.scholar.center.ui.materials.detail

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.scholar.center.R
import com.scholar.center.adapter.TeacherPagerAdapter
import com.scholar.center.databinding.FragmentMaterialDetailBinding
import com.scholar.center.ui.dialogs.LoadingDialogFragment
import com.scholar.center.unit.Constants.BASE_URL
import com.scholar.center.unit.applyDiscount
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MaterialDetailFragment : Fragment(R.layout.fragment_material_detail) {
    private val viewModel: MaterialDetailVM by viewModels()

    private val navController by lazy { findNavController() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMaterialDetailBinding.bind(view)

        binding.materialToolbarBackButton.setOnClickListener {
            navController.popBackStack()
        }

        val tabLayout = binding.materialTabLayout
        val viewPager = binding.materialViewPager

        val fragments = listOf(
            MaterialInformationFragment(viewModel),
            MaterialReviewsFragment(viewModel)
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
        val loadingDialog = LoadingDialogFragment()

        lifecycleScope.launch {
            viewModel.loading.collect { loading ->
                if (loading) {
                    loadingDialog.show(parentFragmentManager, "loading_dialog")
                } else {
                    if (loadingDialog.isVisible)
                        loadingDialog.dismiss()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.message.collect { msg ->
                msg?.let { Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show() }
            }
        }

        lifecycleScope.launch {
            viewModel.alreadyBought.collect { alreadyBought ->
                if (alreadyBought) {
                    binding.materialPriceLayout.visibility = View.GONE
                    binding.materialPriceButton.text = getString(R.string.view)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.material.collect {
                    it?.let { materialWithDetail ->
//                todo        materialAdapter.ad
                        val material = materialWithDetail.material
                        binding.materialName.text = material.title
                        binding.materialType.text = materialWithDetail.category

                        binding.materialClassName.text = getString(
                            R.string.material_class_name,
                            materialWithDetail.stage,
                            materialWithDetail.classroom
                        )
                        val discountedPrice =
                            material.price?.applyDiscount(material.discount ?: 0)
                        if (material.price != null && material.price != 0) {
                            if (material.discount != null && material.discount != 0) {
                                binding.materialDiscount.text = getString(
                                    R.string.price_only, material.price
                                )
                                binding.materialDiscount.paintFlags =
                                    binding.materialDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                                binding.materialPriceValue.text = getString(
                                    R.string.syr, discountedPrice
                                )
                            } else {
                                binding.materialDiscount.visibility = View.GONE
                                binding.materialPriceValue.text =
                                    getString(R.string.syr, material.price)
                            }

                        } else {
                            binding.materialPriceLayout.visibility = View.GONE
                            binding.materialPriceButton.text = getText(R.string.view)
                        }
                        if (materialWithDetail.teacher.name != null) {
                            binding.materialTeacherName.text = materialWithDetail.teacher.name
                            materialWithDetail.teacher.image?.let { image ->
                                Glide.with(requireContext())
                                    .load("${BASE_URL}$image")
                                    .placeholder(R.drawable.ic_person)
                                    .error(R.drawable.ic_person)
                                    .into(binding.materialTeacherImage)
                            }
                        } else {
                            binding.materialTeacherLayout.visibility = View.GONE
                        }


                        binding.materialPriceButton.setOnClickListener {
                            if (!viewModel.isUserLogged.value) {
                                navController.navigate(
                                    MaterialDetailFragmentDirections.actionMaterialDetailToLogin(
                                        mustPopBackStack = true
                                    )
                                )
                                return@setOnClickListener
                            }
                            if(viewModel.alreadyBought.value){
                                navigateTo(materialId = material.id, material.categoryId)
                            }else{
                                viewModel.purchase()
                            }
                        }
                    }
                }
            }
        }

    }

    private fun navigateTo(materialId: Int, category: Int?) {
        if (category == null) return
        if (category == 3) {
            navController.navigate(
                MaterialDetailFragmentDirections.actionMaterialDetailToVideo(
                    materialId = materialId
                )
            )
        } else {
            navController.navigate(
                MaterialDetailFragmentDirections.actionMaterialDetailToPdf(
                    materialId = materialId
                )
            )
        }

    }
}