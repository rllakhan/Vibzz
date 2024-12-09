package com.example.vibzz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignInActivity : AppCompatActivity() {
    private lateinit var etTextEmail: EditText
    private lateinit var etTextPassword: EditText
    private lateinit var tvForgotPassword: TextView
    private lateinit var btnSignIn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etTextEmail = findViewById(R.id.etTextEmailSignIn)
        etTextPassword = findViewById(R.id.etTextPasswordSignIn)
        tvForgotPassword = findViewById(R.id.tvForgotPasswordSignIn)
        btnSignIn = findViewById(R.id.btnSignIn)
        progressBar = findViewById(R.id.progressBarSignIn)

        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmailSignIn)
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPasswordSignIn)

        firebaseAuth = Firebase.auth

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this@SignInActivity, ForgotPassActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnSignIn.setOnClickListener {
            val email = etTextEmail.text.toString()
            val password = etTextPassword.text.toString()

            if (email.isEmpty() && password.isEmpty()) {
                textInputLayoutEmail.error = "Email is required"
                textInputLayoutPassword.error = "Password is required"
            }
            if (email.isEmpty()) textInputLayoutEmail.error = "Email is required"
            if (password.isEmpty()) textInputLayoutPassword.error = "Password is required"

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signIn(email, password)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.VISIBLE
                    val intent = Intent(this@SignInActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@SignInActivity,
                        task.exception?.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}