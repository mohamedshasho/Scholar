package com.scholar.center.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.scholar.center.R
import com.scholar.center.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main) {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        val nestedNavController = childFragmentManager.findFragmentById(R.id.main_nav_host_fragment)?.findNavController()

        nestedNavController?.let { navController ->
            binding.mainNavBottom.setupWithNavController(navController)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


}