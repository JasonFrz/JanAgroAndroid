package com.example.janagroandroid.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.janagroandroid.R

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = host.navController
        findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNav).setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val show = destination.id in setOf(R.id.homeFragment, R.id.cartFragment, R.id.historyFragment, R.id.profileFragment)
            findViewById<View>(R.id.bottomNav).visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}
