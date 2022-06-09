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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {
    private lateinit var userAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private var username: EditText = findViewById(R.id.etUsername)
    private var email: EditText = findViewById(R.id.etEmail)
    private var password: EditText = findViewById(R.id.etPassword)
    private var rePassword: EditText = findViewById(R.id.etConfirmPassword)
    private val register: Button = findViewById(R.id.btnRegister)
    private val accountExist: TextView = findViewById(R.id.tvSignIn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        userAuth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            if (username.text.toString() == "" || email.text.toString() == "" || password.text.toString() == "" || rePassword.text.toString() == "") {
                Toast.makeText(this, "Please fill in all the field", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            } else if (password.text.toString().length < 8) {
                Toast.makeText(this, "Password must be 8 character or above", Toast.LENGTH_SHORT)
                    .show()
            } else if (password.text.toString() != rePassword.text.toString()) {
                Toast.makeText(this, "Password confirmation does not match", Toast.LENGTH_SHORT)
                    .show()
            } else {
                registerAccount(
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString()
                )
            }
        }

        accountExist.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerAccount(username: String, email: String, password: String) {
        userAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user: FirebaseUser = userAuth.currentUser!!

                database = FirebaseDatabase.getInstance()
                val reference: DatabaseReference = database.getReference("Users")

                val uid = user.uid
                val hashMap: HashMap<Any, String> = HashMap()
                hashMap["email"] = email
                hashMap["uid"] = uid
                hashMap["name"] = username
                hashMap["role"] = "user"
                hashMap["onlineStatus"] = "online"
                hashMap["typingTo"] = "noOne"
                hashMap["profilePicture"] = ""

                reference.child(uid).setValue(hashMap)

                Toast.makeText(this, "Welcome to StudyTimes", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show()
            }
    }
}