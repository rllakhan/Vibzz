package com.example.vibzz

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.SyncTree

class CreatePostActivity : AppCompatActivity() {
    private lateinit var etTextPostDescription: EditText
    private lateinit var btnPublish: Button
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etTextPostDescription = findViewById(R.id.etTextPostDescription)
        btnPublish = findViewById(R.id.btnPublish)

        databaseReference = FirebaseDatabase.getInstance().getReference("tweets")

        btnPublish.setOnClickListener {
            val tweetText = etTextPostDescription.text.toString()

            if (tweetText.isEmpty()) {
                etTextPostDescription.error = "This field is required"
            }
            if (tweetText.isNotEmpty()) {
                val id = System.currentTimeMillis().toString()
                val tweet = Tweet(id, tweetText)
                databaseReference.child(id).setValue(tweet)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@CreatePostActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@CreatePostActivity,
                                task.exception?.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }


    }
}