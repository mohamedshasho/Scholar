package com.scholar.center

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.scholar.center.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = binding.mainNavBottom
        bottomNavigationView.setupWithNavController(navController)

//        bottomNavigationView.setOnItemReselectedListener { menu ->
//            when (menu.itemId) {
//                R.id.menu_home -> {
//                    navController.navigate(R.)
//                }
//                R.id.menu_teachers -> {
//                    // Handle search button click
//
//                }
//                R.id.menu_stories -> {
//                    // Handle profile button click
//
//                }
//                R.id.menu_profile -> {
//                    // Handle profile button click
//
//                }
//            }
//        }
        // todo
    }
}