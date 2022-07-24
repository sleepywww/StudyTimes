@file:Suppress("DEPRECATION")

package com.fyp.studytimes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.navBottom)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}