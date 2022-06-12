package com.fyp.studytimes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class PasswordRecoveryActivity : AppCompatActivity() {
    private lateinit var back: Button
    private lateinit var email: EditText
    private lateinit var sentTxt: TextView
    private lateinit var sentEmailTxt: TextView
    private lateinit var reset: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_recovery)

        initVar() // Initialize variable in this activity

        back.setOnClickListener {
            finish() // Direct user back to previous page
        }

        reset.setOnClickListener {
            checkEmail(email.text.toString()) // Check email input
        }
    }

    private fun initVar() {
        back = findViewById(R.id.btnBack)
        email = findViewById(R.id.etForgetEmail)
        sentTxt = findViewById(R.id.tvLinkSent)
        sentEmailTxt = findViewById(R.id.tvResetEmail)
        reset = findViewById(R.id.btnReset)
    }

    private fun checkEmail(emailInput: String) {
        if (emailInput == "") { // Check if email input is empty
            email.error = "Please Enter An Email"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput)
                .matches()
        ) { // Check if email format is valid
            email.error = "Please Enter Valid Email"
        } else {
            mAuth.fetchSignInMethodsForEmail(emailInput) // Check if account with email input existed
                .addOnSuccessListener {
                    sendEmail(emailInput) // Send reset email
                }
                .addOnFailureListener {
                    Toast.makeText(this@PasswordRecoveryActivity, "Error", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun sendEmail(emailInput: String) {
        mAuth.sendPasswordResetEmail(emailInput) // Send reset email to the email input
            .addOnSuccessListener { // Make success message prompt to user
                sentTxt.visibility = View.VISIBLE
                sentEmailTxt.text = email.text.toString()
                sentEmailTxt.visibility = View.VISIBLE
            }
            .addOnFailureListener {
                Toast.makeText(this@PasswordRecoveryActivity, "Error", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}