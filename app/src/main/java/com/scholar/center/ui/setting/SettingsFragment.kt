package com.scholar.center.ui.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.scholar.center.R
import com.scholar.center.databinding.FragmentProfileBinding
import com.scholar.center.ui.main.MainFragmentDirections
import com.scholar.center.unit.Constants.BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: SettingsVM by viewModels()

    private val navController by lazy { activity?.findNavController(R.id.root_nav_host_fragment) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProfileBinding.bind(view)
        lifecycleScope.launch {
            viewModel.isStudentLoggedIn.collectLatest { isUserLogin ->
                if (isUserLogin) {
                    showItems()
                } else {
                    hideItems()
                }

                binding.profileFragmentLogoutListTile.root.setOnClickListener {
                    if (!isUserLogin) {
                        navController?.navigate(
                            MainFragmentDirections.actionMainToLogin(
                                mustPopBackStack = true
                            )
                        )
                    } else {
                        viewModel.logout()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.student.collect { student ->
                    student?.let {
                        binding.profileFragmentStudentName.text = student.fullName
                        binding.profileWalletAmount.text = student.wallet ?: "0"
                        student.image?.let {
                            Glide.with(requireContext())
                                .load("${BASE_URL}${it}")
                                .into(binding.profileFragmentImage)
                        }
                    }
                }
            }
        }

        binding.profileFragmentMaterialListTile.icon.setImageResource(R.drawable.ic_material)
        binding.profileFragmentMaterialListTile.text.text = getString(R.string.material)
        binding.profileFragmentFavoriteListTile.icon.setImageResource(R.drawable.ic_favorite)
        binding.profileFragmentFavoriteListTile.text.text = getString(R.string.favorites)
        binding.profileFragmentAccountListTile.icon.setImageResource(R.drawable.ic_account)
        binding.profileFragmentAccountListTile.text.text = getString(R.string.account)
        binding.profileFragmentLanguageListTile.icon.setImageResource(R.drawable.ic_language)
        binding.profileFragmentLanguageListTile.text.text = getString(R.string.change_lan)
        binding.profileFragmentLogoutListTile.icon.setImageResource(R.drawable.ic_logout)
        binding.profileFragmentCashListTile.icon.setImageResource(R.drawable.wallet)
        binding.profileFragmentCashListTile.text.text = getString(R.string.purchase_credit)

        binding.profileFragmentCashListTile.root.setOnClickListener {
            navController?.navigate(MainFragmentDirections.actionMainToCredit())
        }


    }


    private fun showItems() {
        binding.profileFragmentAccountListTile.root.visibility = View.VISIBLE
        binding.profileFragmentCashListTile.root.visibility = View.VISIBLE
        binding.profileFragmentCashListTile.root.visibility = View.VISIBLE
        binding.profileWalletLayout.visibility = View.VISIBLE
        binding.profileFragmentLogoutListTile.text.text = getString(R.string.logout)
    }

    private fun hideItems() {
        binding.profileFragmentStudentName.text = getString(R.string.guest)
        binding.profileFragmentAccountListTile.root.visibility = View.GONE
        binding.profileFragmentCashListTile.root.visibility = View.GONE
        binding.profileFragmentCashListTile.root.visibility = View.GONE
        binding.profileWalletLayout.visibility = View.GONE
        binding.profileFragmentLogoutListTile.text.text = getString(R.string.login)
    }
}