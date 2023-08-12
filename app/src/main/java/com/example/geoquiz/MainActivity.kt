package com.example.geoquiz

import Question
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.prevButton.setOnClickListener {
            quizViewModel.movetoPrev()
            updateQuestion()
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
        val answered = quizViewModel.currentQuestionUserAnswered
        binding.trueButton.setEnabled(!answered)
        binding.falseButton.setEnabled(!answered)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        quizViewModel.setQuestionAnswered()

        val correct: Boolean = userAnswer == correctAnswer

        val messageResId = if (correct) R.string.correct_toast else R.string.incorrect_toast

        if (correct) quizViewModel.setQuestionCorrect()

        binding.trueButton.setEnabled(false)
        binding.falseButton.setEnabled(false)

        checkComplete()
//        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun checkComplete() {
        if (!quizViewModel.quizComplete) return
        val finalScore = quizViewModel.finalScore
        val finalScoreText = resources.getString(R.string.final_score) + ": $finalScore%"
        Toast.makeText(this, finalScoreText, Toast.LENGTH_SHORT).show()
    }
}