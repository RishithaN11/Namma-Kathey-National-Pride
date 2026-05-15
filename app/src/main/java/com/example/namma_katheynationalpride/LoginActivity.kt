package com.example.namma_katheynationalpride

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val loginBtn = findViewById<Button>(R.id.loginBtn)

        val session = UserSession(this)

        loginBtn.setOnClickListener {

            val name = nameInput.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name 😊", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // save user name
            session.saveUser(name)

            // go to main app
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}