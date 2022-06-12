package com.fyp.studytimes

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var forgetPassword: TextView
    private lateinit var registerHere: TextView
    private lateinit var pDialog: ProgressDialog
    private lateinit var userAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initVar() // Initialize variable in this activity

        login.setOnClickListener {
            if (email.text.toString() == "" || password.text.toString() == "") { // Check if input field is empty
                Toast.makeText(this@LoginActivity, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email.text.toString(), password.text.toString()) // Login user
            }
        }

        forgetPassword.setOnClickListener { // Direct user to recover password
            val intent= Intent(this@LoginActivity, PasswordRecoveryActivity::class.java)
            startActivity(intent)
        }

        registerHere.setOnClickListener { // Direct user to register account
            val intent= Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initVar() {
        email = findViewById(R.id.etLoginEmail)
        password = findViewById(R.id.etLoginPassword)
        login = findViewById(R.id.btnLogin)
        forgetPassword = findViewById(R.id.tvForgetPassword)
        registerHere = findViewById(R.id.tvSignUpHere)

        pDialog = ProgressDialog(this@LoginActivity)
        pDialog.setMessage("Loading")
        pDialog.setCanceledOnTouchOutside(false)

        userAuth = FirebaseAuth.getInstance()
    }

    private fun loginUser(emailInput: String, passwordInput: String) {
        userAuth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnSuccessListener { // Direct user to main page
                val intent= Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this@LoginActivity, "Incorrect email or password", Toast.LENGTH_SHORT).show()
            }
    }
}