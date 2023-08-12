package com.scholar.center.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.scholar.center.R
import com.scholar.center.databinding.FragmentProfileBinding
import com.scholar.center.ui.MainFragmentDirections

class SettingsFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    private val navController by lazy {  activity?.findNavController(R.id.root_nav_host_fragment) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)

        val isUserLogin = true
        if(isUserLogin){
            binding.profileFragmentStudentName.text = getString(R.string.guest)
            binding.profileFragmentAccountListTile.root.visibility = View.GONE
            binding.profileFragmentCashListTile.root.visibility = View.GONE
            binding.profileFragmentCashListTile.root.visibility = View.GONE
            binding.profileWalletLayout.visibility = View.GONE
            binding.profileFragmentLogoutListTile.text.text = getString(R.string.login)
            binding.profileFragmentLogoutListTile.root.setOnClickListener {
                navController?.navigate(MainFragmentDirections.actionMainToLogin())
            }
        }else{
            binding.profileFragmentLogoutListTile.text.text = getString(R.string.logout)
            binding.profileFragmentLogoutListTile.root.setOnClickListener {
            }
        }



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProfileBinding.bind(view)
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


    }
}