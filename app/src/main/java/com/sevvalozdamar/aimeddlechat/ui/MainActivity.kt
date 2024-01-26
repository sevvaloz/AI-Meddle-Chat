package com.sevvalozdamar.aimeddlechat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sevvalozdamar.aimeddlechat.databinding.ActivityMainBinding
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.utils.gone
import com.sevvalozdamar.aimeddlechat.utils.viewBinding
import com.sevvalozdamar.aimeddlechat.utils.visible

class MainActivity : AppCompatActivity() {
    
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavView = findViewById(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNavView, navController)
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.profileFragment -> {
                    bottomNavView.visible()
                }

                else -> {
                    bottomNavView.gone()
                }
            }
        }
    }
}