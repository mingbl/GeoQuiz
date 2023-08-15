package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "CheatViewModel"
const val CHEATED_KEY = "CHEATED_KEY"

class CheatViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    var cheated: Boolean
        get() = savedStateHandle.get(CHEATED_KEY) ?: false
        set(value) = savedStateHandle.set(CHEATED_KEY, value)
}