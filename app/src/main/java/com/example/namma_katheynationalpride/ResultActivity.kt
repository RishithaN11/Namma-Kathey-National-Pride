package com.example.namma_katheynationalpride

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 60, 40, 40)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)
        val heroId = intent.getStringExtra("heroId") ?: ""

        // SAVE BADGE
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("Badges", MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.putBoolean(heroId, true)

        editor.apply()

        // TITLE
        val title = TextView(this)
        title.text = "🎉 Quiz Completed!"
        title.textSize = 28f

        // SCORE
        val scoreText = TextView(this)
        scoreText.text = "Score: $score / $total"
        scoreText.textSize = 22f

        // BADGE MESSAGE
        val badgeText = TextView(this)
        badgeText.text = "🏅 Heritage Badge Earned!"
        badgeText.textSize = 22f

        val heroText = TextView(this)
        heroText.text = "Hero ID = $heroId"
        heroText.textSize = 18f

        layout.addView(heroText)
        // HOME BUTTON
        val homeButton = Button(this)
        homeButton.text = "🏠 Back To Home"

        homeButton.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)

            finish()
        }

        layout.addView(title)
        layout.addView(scoreText)
        layout.addView(badgeText)
        layout.addView(homeButton)

        setContentView(layout)
    }
}