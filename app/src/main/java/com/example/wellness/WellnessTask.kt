package com.example.wellness

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class WellnessTask(
    val id: Int,
    val label: String,
    initialCheck: Boolean = false
) {
    var checked by mutableStateOf(initialCheck)
}
