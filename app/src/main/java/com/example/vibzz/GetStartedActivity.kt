package com.example.vibzz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GetStartedActivity : AppCompatActivity() {
    private lateinit var btnCreateAccount: Button
    private lateinit var tvSignIn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_started)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        tvSignIn = findViewById(R.id.tvSignIn)

        btnCreateAccount.setOnClickListener {
            val intent = Intent(this@GetStartedActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvSignIn.setOnClickListener {
            val intent = Intent(this@GetStartedActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}