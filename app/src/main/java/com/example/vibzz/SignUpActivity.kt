package com.example.vibzz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var etTextName: EditText
    private lateinit var etTextEmail: EditText
    private lateinit var etTextPassword: EditText
    private lateinit var etTextConfirmPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputLayoutConfirmPassword: TextInputLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etTextName = findViewById(R.id.etTextNameSignUp)
        etTextEmail = findViewById(R.id.etTextEmailSignUp)
        etTextPassword = findViewById(R.id.etTextPasswordSignUp)
        etTextConfirmPassword = findViewById(R.id.etTextConfirmPassword)

        btnSignUp = findViewById(R.id.btnSignUp)
        progressBar = findViewById(R.id.progressBarSignUp)

        textInputLayoutName = findViewById(R.id.textInputLayoutNameSignUp)
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmailSignUp)
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPasswordSignUp)
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPasswordSignUp)


        firebaseAuth = Firebase.auth
        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        btnSignUp.setOnClickListener {
            val name = etTextName.text.toString()
            val email = etTextEmail.text.toString()
            val password = etTextPassword.text.toString()
            val confirmPassword = etTextConfirmPassword.text.toString()

            if (name.isEmpty()
                && email.isEmpty()
                && password.isEmpty()
                && confirmPassword.isEmpty()) {
                textInputLayoutName.error = "Name is required"
                textInputLayoutEmail.error = "Email is required"
                textInputLayoutPassword.error = "Password is required"
                textInputLayoutConfirmPassword.error = "Password is required"
            }
            if (name.isEmpty()) textInputLayoutName.error = "Name is required"
            if (email.isEmpty()) textInputLayoutEmail.error = "Email is required"
            if (password.isEmpty()) textInputLayoutPassword.error = "Password is required"
            if (confirmPassword.isEmpty()) textInputLayoutConfirmPassword.error = "Password is required"

            if (name.isNotEmpty()
                && email.isNotEmpty()
                && password.isNotEmpty()
                && confirmPassword.isNotEmpty()) {

                if (password == confirmPassword) {
                    signUp(name, email, password)
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Password didn't match",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    private fun signUp(name: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.VISIBLE
                    val id = firebaseAuth.currentUser?.uid.toString()
                    val user = User(id, name, email)
                    databaseReference.child(id).setValue(user)
                        .addOnCompleteListener(this) { t ->
                            if (t.isSuccessful) {
                                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@SignUpActivity,
                                    t.exception?.localizedMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                                firebaseAuth.currentUser?.delete()
                            }
                        }
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        task.exception?.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}