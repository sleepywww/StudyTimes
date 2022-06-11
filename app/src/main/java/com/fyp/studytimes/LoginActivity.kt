package com.fyp.studytimes

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class LoginActivity : AppCompatActivity() {
    private var loginEmail: EditText? = null
    private var loginPassword: EditText? = null
    private var loginBtn: Button? = null
    private var registerHere: TextView? = null
    private var pDialog: ProgressDialog? = null
    private var userAuth: FirebaseAuth? = null
    private var databaseRef: DatabaseReference? = null
    private var loggedInUser: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    override fun onStart() {
        super.onStart()
        if (CometChat.getLoggedInUser() != null) {
            goToMainActivity();
        }
    }
}