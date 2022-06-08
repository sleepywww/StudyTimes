package com.fyp.studytimes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var currentUser: FirebaseUser
    private lateinit var userAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        userAuth = FirebaseAuth.getInstance()
        if (userAuth != null) {
            currentUser = userAuth.currentUser!!
        }
        Handler().postDelayed( {
            val user: FirebaseUser = userAuth.currentUser!!
            if (user == null) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val mainIntent = Intent(this, DashboardActivity::class.java)
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(mainIntent)
                finish()
            }
        }, 3000)
    }
}