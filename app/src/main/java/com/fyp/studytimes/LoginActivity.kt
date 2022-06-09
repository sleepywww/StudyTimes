package com.fyp.studytimes

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var currentUser: FirebaseUser
    private lateinit var userAuth: FirebaseAuth
    private var email: EditText = findViewById(R.id.etLoginEmail)
    private var password: EditText = findViewById(R.id.etLoginPassword)
    private val login: Button = findViewById(R.id.btnLogin)
    private val notRegistered: TextView = findViewById(R.id.tvSignUpHere)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userAuth = FirebaseAuth.getInstance()
        if (userAuth != null) {
            currentUser = userAuth.currentUser!!
        }

        login.setOnClickListener {
            if (email.text.toString() == "" || password.text.toString() == "") {
                Toast.makeText(this, "Please fill in all the field", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email.text.toString(), password.text.toString())
            }
        }

        notRegistered.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        userAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
            }
    }
}