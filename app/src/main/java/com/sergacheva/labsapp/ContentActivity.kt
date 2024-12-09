package com.sergacheva.labsapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_navigation.setupWithNavController(navController)

        // Для экрана загрузки, логина и регистарции нижнюю панель скрываем
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    bottom_navigation.visibility = View.GONE
                }
                R.id.loginFragment -> {
                    bottom_navigation.visibility = View.GONE
                }
                R.id.registrationFragment -> {
                    bottom_navigation.visibility = View.GONE
                }
                else -> {
                    bottom_navigation.visibility = View.VISIBLE
                }
            }
        }
    }
}