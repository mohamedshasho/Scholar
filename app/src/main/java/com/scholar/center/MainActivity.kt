package com.scholar.center

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
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
            supportFragmentManager.findFragmentById(binding.rootNavHostFragment.id) as NavHostFragment

        val navController = navHostFragment.navController

        val startDestination = if (true) {
            R.id.mainFragment // Replace with the ID of your home fragment
        } else {
            R.id.loginFragment // Replace with the ID of your login fragment
        }
        val navGraph = navController.navInflater.inflate(R.navigation.root_nav_graph)
        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph




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