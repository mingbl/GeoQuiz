package com.example.geoquiz

import Question
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionUserAnswered: Boolean
        get() = questionBank[currentIndex].userAnswered
    val currentQuestionUserCorrect: Boolean
        get() = questionBank[currentIndex].userCorrect
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    val quizComplete: Boolean
        get() = isQuizComplete()
    val finalScore: Int
        get() = calculateTotalScore()

    private fun isQuizComplete(): Boolean {
        for (q in questionBank) {
            if (!q.userAnswered) return false
        }
        return true
    }
    private fun getTotalScore(): Int {
        var score: Int = 0
        for (q in questionBank) {
            if (q.userCorrect) score++
        }
        return score
    }
    private fun calculateTotalScore(): Int {
        return ((getTotalScore().toDouble() / questionBank.size) * 100).roundToInt()
    }
    fun setQuestionAnswered() {
        questionBank[currentIndex].userAnswered = true
    }
    fun setQuestionCorrect() {
        questionBank[currentIndex].userCorrect = true
    }
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun movetoPrev() {
        currentIndex = (questionBank.size + currentIndex - 1) % questionBank.size
    }
}