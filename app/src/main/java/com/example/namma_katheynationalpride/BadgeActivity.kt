package com.example.namma_katheynationalpride

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class BadgeActivity : AppCompatActivity() {

    private lateinit var badgeContainer: LinearLayout
    private lateinit var backBadgeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge)

        badgeContainer = findViewById(R.id.badgeContainer)
        backBadgeButton = findViewById(R.id.backBadgeButton)

        backBadgeButton.setOnClickListener {
            finish()
        }

        val sharedPreferences =
            getSharedPreferences("Badges", MODE_PRIVATE)

        // ✅ LOAD JSON (DYNAMIC HERO LIST)
        val jsonString = assets.open("heroes.json")
            .bufferedReader()
            .use { it.readText() }

        val jsonObject = JSONObject(jsonString)
        val heroes = jsonObject.getJSONObject("heroes")

        val keys = heroes.keys()

        while (keys.hasNext()) {

            val key = keys.next()
            val hero = heroes.getJSONObject(key)

            val heroName = hero.optString("name", "Unknown Hero")

            val earned = sharedPreferences.getBoolean(key, false)

            val card = LinearLayout(this)
            card.orientation = LinearLayout.VERTICAL
            card.setPadding(30, 30, 30, 30)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(0, 0, 0, 25)
            card.layoutParams = params

            if (earned) {
                card.setBackgroundColor(Color.parseColor("#FFE082"))
            } else {
                card.setBackgroundColor(Color.parseColor("#E0E0E0"))
            }

            val heroNameView = TextView(this)
            heroNameView.text = heroName
            heroNameView.textSize = 24f
            heroNameView.setTextColor(Color.BLACK)

            val status = TextView(this)

            status.text = if (earned) "✅ Badge Earned" else "🔒 Locked"
            status.textSize = 18f
            status.setPadding(0, 12, 0, 0)

            card.addView(heroNameView)
            card.addView(status)

            badgeContainer.addView(card)
        }
    }
}