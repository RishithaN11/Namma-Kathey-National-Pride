package com.example.namma_katheynationalpride

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.util.Locale

class StoryActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var storyText: TextView
    private lateinit var languageButton: Button
    private lateinit var btnTakeQuiz: Button
    private lateinit var btnBack: Button
    private lateinit var readButton: Button
    private lateinit var btnViewStatue: Button

    private lateinit var textToSpeech: TextToSpeech

    private var englishStory = ""
    private var kannadaStory = ""
    private var isEnglish = true
    private var ttsReady = false

    private var heroName = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        storyText = findViewById(R.id.storyText)
        languageButton = findViewById(R.id.languageButton)
        btnTakeQuiz = findViewById(R.id.btnTakeQuiz)
        btnBack = findViewById(R.id.btnBack)
        readButton = findViewById(R.id.readButton)
        btnViewStatue = findViewById(R.id.btnViewStatue)

        textToSpeech = TextToSpeech(this, this)

        val heroId = intent.getStringExtra("heroId") ?: ""

        if (heroId.isEmpty()) {
            finish()
            return
        }

        val jsonString = assets.open("heroes.json")
            .bufferedReader()
            .use { it.readText() }

        val jsonObject = JSONObject(jsonString)
        val hero = jsonObject.getJSONObject("heroes").getJSONObject(heroId)

        heroName = hero.getString("name")

        englishStory = hero.getString("storyEnglish")
        kannadaStory = hero.getString("storyKannada")

        storyText.text = englishStory

        // LANGUAGE SWITCH
        languageButton.setOnClickListener {
            if (isEnglish) {
                storyText.text = kannadaStory
                languageButton.text = "🌐 English"
            } else {
                storyText.text = englishStory
                languageButton.text = "🌐 ಕನ್ನಡ"
            }
            isEnglish = !isEnglish
        }

        // READ STORY
        readButton.setOnClickListener {
            if (!ttsReady) {
                Toast.makeText(this, "TTS not ready", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            textToSpeech.speak(
                storyText.text.toString(),
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
        }

        // QUIZ
        btnTakeQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("heroId", heroId)
            startActivity(intent)
        }

        // BACK
        btnBack.setOnClickListener {
            finish()
        }

        // 🏛 STATUE (FINAL FIX - SEARCH BASED)
        btnViewStatue.setOnClickListener {

            val query = Uri.encode("$heroName statue Karnataka")

            val uri = Uri.parse(
                "https://www.google.com/maps/search/?api=1&query=$query"
            )

            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.US)
            ttsReady = true
        }
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}