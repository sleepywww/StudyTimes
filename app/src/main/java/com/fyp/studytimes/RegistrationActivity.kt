@file:Suppress("DEPRECATION")

package com.fyp.studytimes

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegistrationActivity : AppCompatActivity() {
    private lateinit var back: Button
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var agree: CheckBox
    private lateinit var term: TextView
    private lateinit var register: Button
    private lateinit var loginHere: TextView
    private lateinit var pDialog: ProgressDialog
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initVar() // Initialize variable in this activity

        back.setOnClickListener {
            finish() // Direct user back to previous page
        }

        term.setOnClickListener {

        }

        register.setOnClickListener {
            checkInput(
                username.text.toString(),
                email.text.toString(),
                password.text.toString(),
                confirmPassword.text.toString()
            )
        }

        loginHere.setOnClickListener {

        }
    }

    private fun initVar() {
        back = findViewById(R.id.btnBack)
        username = findViewById(R.id.etRegisterUsername)
        email = findViewById(R.id.etRegisterEmail)
        password = findViewById(R.id.etRegisterPassword)
        confirmPassword = findViewById(R.id.etRegisterConfirmPassword)
        agree = findViewById(R.id.cbAgree)
        term = findViewById(R.id.tvTerms)
        register = findViewById(R.id.btnRegister)
        loginHere = findViewById(R.id.tvSignInHere)

        pDialog = ProgressDialog(this@RegistrationActivity)
        pDialog.setMessage("Loading")
        pDialog.setCanceledOnTouchOutside(false)

        mAuth = FirebaseAuth.getInstance()
    }

    private fun checkInput(
        usernameInput: String,
        emailInput: String,
        passwordInput: String,
        confirmPasswordInput: String
    ) {
        if (usernameInput == "") { // Check if username input is empty
            username.error = "Please Enter An Username"
        } else if (emailInput == "") { // Check if email input is empty
            email.error = "Please Enter An Email"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput)
                .matches()
        ) { // Check if email format is valid
            email.error = "Please Enter An Valid Email"
        } else if (passwordInput == "") { // Check if password input is empty
            password.error = "Please Enter A Password"
        } else if (password.length() < 8) {
            password.error = "Password Length Must At Least 8 Character"
        } else if (confirmPasswordInput != passwordInput) {
            confirmPassword.error = "The Password Does Not Match"
        } else {
            mAuth.fetchSignInMethodsForEmail(emailInput) // Check if account with email input existed
                .addOnSuccessListener {
                    registerUser(emailInput, passwordInput) // Login user
                }
                .addOnFailureListener {
                    Toast.makeText(this@RegistrationActivity, "Error", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun registerUser(emailInput: String, passwordInput: String) {
        pDialog.show()

        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
            .addOnSuccessListener {
                pDialog.dismiss()

                mUser = mAuth.currentUser!!
            }
            .addOnFailureListener {
                pDialog.dismiss()

                Toast.makeText(this@RegistrationActivity, "Error", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}