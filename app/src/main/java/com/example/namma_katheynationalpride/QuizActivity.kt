package com.example.namma_katheynationalpride

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class QuizActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var questionNumberText: TextView

    private lateinit var option1: Button
    private lateinit var option2: Button
    private lateinit var option3: Button
    private lateinit var option4: Button

    private lateinit var backButton: Button

    private var quizList = mutableListOf<JSONObject>()

    private var currentIndex = 0
    private var score = 0

    private var heroId = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionText = findViewById(R.id.questionText)
        questionNumberText = findViewById(R.id.questionNumberText)

        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)

        backButton = findViewById(R.id.backQuizButton)

        // GET HERO ID

        heroId = intent.getStringExtra("heroId") ?: ""

        // IMPORTANT CHECK

        if (heroId.isEmpty()) {
            finish()
            return
        }

        loadQuiz(heroId)

        // SAFETY CHECK

        if (quizList.isEmpty()) {
            finish()
            return
        }

        showQuestion()

        option1.setOnClickListener {
            checkAnswer(option1.text.toString())
        }

        option2.setOnClickListener {
            checkAnswer(option2.text.toString())
        }

        option3.setOnClickListener {
            checkAnswer(option3.text.toString())
        }

        option4.setOnClickListener {
            checkAnswer(option4.text.toString())
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun loadQuiz(heroId: String) {

        try {

            val jsonString = assets.open("heroes.json")
                .bufferedReader()
                .use { it.readText() }

            val jsonObject = JSONObject(jsonString)

            val heroes = jsonObject.getJSONObject("heroes")

            val hero = heroes.getJSONObject(heroId)

            val quizArray = hero.getJSONArray("quiz")

            quizList.clear()

            for (i in 0 until quizArray.length()) {

                quizList.add(
                    quizArray.getJSONObject(i)
                )
            }

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    private fun showQuestion() {

        if (currentIndex >= quizList.size) {

            showResult()

            return
        }

        val question = quizList[currentIndex]

        questionText.text =
            question.getString("question")

        val options =
            question.getJSONArray("options")

        option1.text = options.getString(0)
        option2.text = options.getString(1)
        option3.text = options.getString(2)
        option4.text = options.getString(3)

        questionNumberText.text =
            "Question ${currentIndex + 1} / ${quizList.size}"
    }

    private fun checkAnswer(selected: String) {

        val correct =
            quizList[currentIndex]
                .getString("answer")

        if (selected == correct) {

            score += 10
        }

        currentIndex++

        showQuestion()
    }

    private fun showResult() {

        val intent =
            Intent(this, ResultActivity::class.java)

        intent.putExtra("score", score)

        intent.putExtra(
            "total",
            quizList.size * 10
        )

        intent.putExtra(
            "heroId",
            heroId
        )

        startActivity(intent)

        finish()
    }
}