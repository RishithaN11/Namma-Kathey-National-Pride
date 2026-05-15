package com.example.namma_katheynationalpride

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class HeroActivity : AppCompatActivity() {

    private lateinit var heroContainer: LinearLayout
    private lateinit var homeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero)

        heroContainer = findViewById(R.id.heroContainer)
        homeButton = findViewById(R.id.homeButton)

        loadHeroes()

        homeButton.setOnClickListener {
            finish()
        }
    }

    private fun loadHeroes() {

        val districtFilter = intent.getStringExtra("district")
            ?.lowercase()
            ?.trim()
            ?: ""

        val jsonString = assets.open("heroes.json")
            .bufferedReader()
            .use { it.readText() }

        val jsonObject = JSONObject(jsonString)
        val heroes = jsonObject.getJSONObject("heroes")

        heroContainer.removeAllViews()

        val keys = heroes.keys()

        while (keys.hasNext()) {

            val key = keys.next()
            val hero = heroes.getJSONObject(key)

            // ✅ SAFE parsing (NO CRASH)
            val heroName = hero.optString("name", "Unknown Hero")
            val heroDistrict = hero.optString("district", "unknown")
                .lowercase()
                .trim()

            if (districtFilter.isNotEmpty() && heroDistrict != districtFilter) {
                continue
            }

            val button = Button(this)
            button.text = heroName
            button.textSize = 18f
            button.setBackgroundColor(Color.parseColor("#1565C0"))
            button.setTextColor(Color.WHITE)

            heroContainer.addView(button)

            button.setOnClickListener {
                val intent = Intent(this, StoryActivity::class.java)
                intent.putExtra("heroId", key)
                startActivity(intent)
            }
        }
    }
}