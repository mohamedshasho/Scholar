package com.scholar.center.ui.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.scholar.center.R
import com.scholar.center.databinding.FragmentProfileBinding

class SettingsFragment : Fragment(R.layout.fragment_profile) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentProfileBinding.bind(view)
        binding.profileFragmentMaterialListTile.icon.setImageResource(R.drawable.ic_material)
        binding.profileFragmentMaterialListTile.text.text = getString(R.string.material)
        binding.profileFragmentFavoriteListTile.icon.setImageResource(R.drawable.ic_favorite)
        binding.profileFragmentFavoriteListTile.text.text = getString(R.string.favorites)
        binding.profileFragmentAccountListTile.icon.setImageResource(R.drawable.ic_account)
        binding.profileFragmentAccountListTile.text.text = getString(R.string.account)
        binding.profileFragmentLanguageListTile.icon.setImageResource(R.drawable.ic_language)
        binding.profileFragmentLanguageListTile.text.text = getString(R.string.change_lan)
        binding.profileFragmentLogoutListTile.icon.setImageResource(R.drawable.ic_logout)
        binding.profileFragmentLogoutListTile.text.text = getString(R.string.logout)



    }
}