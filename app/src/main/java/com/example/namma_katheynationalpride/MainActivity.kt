package com.example.namma_katheynationalpride

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var exploreBtn: Button
    private lateinit var badgeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exploreBtn = findViewById(R.id.exploreBtn)
        badgeBtn = findViewById(R.id.badgeBtn)

        // ✅ FIXED: Go to District screen instead of HeroActivity
        exploreBtn.setOnClickListener {

            val intent = Intent(this, DistrictActivity::class.java)
            startActivity(intent)

            Toast.makeText(
                this,
                "Select District 📍",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Badge screen (unchanged)
        badgeBtn.setOnClickListener {

            val intent = Intent(this, BadgeActivity::class.java)
            startActivity(intent)
        }
    }
}