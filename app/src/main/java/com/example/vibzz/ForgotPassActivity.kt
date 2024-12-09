package com.example.vibzz

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ForgotPassActivity : AppCompatActivity() {
    private lateinit var etTextEmail: EditText
    private lateinit var btnSend: Button
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var textInputLayoutEmail: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_pass)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etTextEmail = findViewById(R.id.etTextEmailForgotPass)
        btnSend = findViewById(R.id.btnSend)
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmailForgotPass)

        firebaseAuth = Firebase.auth

        btnSend.setOnClickListener {
            val email = etTextEmail.text.toString()

            if (email.isEmpty()) {
                textInputLayoutEmail.error = "Email is required"
            }
            if (email.isNotEmpty()) {
                sendResetLink(email)
            }
        }
    }

    private fun sendResetLink(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@ForgotPassActivity,
                        "Check your email",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this@ForgotPassActivity, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
    }
}