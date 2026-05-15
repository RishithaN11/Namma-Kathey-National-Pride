package com.example.namma_katheynationalpride

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DistrictActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_district)

        findViewById<Button>(R.id.btnBengaluru).setOnClickListener { openHeroes("bengaluru") }
        findViewById<Button>(R.id.btnMysuru).setOnClickListener { openHeroes("mysuru") }
        findViewById<Button>(R.id.btnBelagavi).setOnClickListener { openHeroes("belagavi") }
        findViewById<Button>(R.id.btnShivamogga).setOnClickListener { openHeroes("shivamogga") }
        findViewById<Button>(R.id.btnChitradurga).setOnClickListener { openHeroes("chitradurga") }
        findViewById<Button>(R.id.btnUdupi).setOnClickListener { openHeroes("udupi") }
        findViewById<Button>(R.id.btnHampi).setOnClickListener { openHeroes("hampi") }
    }

    private fun openHeroes(district: String) {
        val intent = Intent(this, HeroActivity::class.java)
        intent.putExtra("district", district.lowercase().trim())
        startActivity(intent)
    }
}