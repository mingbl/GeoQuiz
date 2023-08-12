import androidx.annotation.StringRes
data class Question(@StringRes val textResId: Int, val answer: Boolean, var userAnswered: Boolean = false, var userCorrect: Boolean = false)