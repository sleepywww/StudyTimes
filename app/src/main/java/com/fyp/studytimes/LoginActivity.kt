@file:Suppress("DEPRECATION")

package com.fyp.studytimes

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
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
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initVar() // Initialize variable in this activity

        login.setOnClickListener {
            checkInput(email.text.toString(), password.text.toString())
        }

        forgetPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, PasswordRecoveryActivity::class.java)
            startActivity(intent) // Direct user to recover password
        }

        registerHere.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent) // Direct user to register account
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

        mAuth = FirebaseAuth.getInstance()
    }

    private fun checkInput(emailInput: String, passwordInput: String) {
        if (emailInput == "") { // Check if email input is empty
            email.error = "Please Enter An Email"
        } else if (passwordInput == "") { // Check if password input is empty
            password.error = "Please Enter A Password"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput)
                .matches()
        ) { // Check if email format is valid
            email.error = "Please Enter An Valid Email"
        } else {  // If everything was correct
            loginUser(emailInput, passwordInput) // Login user
        }
    }

    private fun loginUser(emailInput: String, passwordInput: String) {
        pDialog.show()

        mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnSuccessListener {
                pDialog.dismiss()

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent) // Direct user to main page
                finish()
            }
            .addOnFailureListener {
                pDialog.dismiss()

                Toast.makeText(
                    this@LoginActivity,
                    "Incorrect email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}