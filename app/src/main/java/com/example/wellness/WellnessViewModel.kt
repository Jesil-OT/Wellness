package com.example.wellness

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel: ViewModel() {

    private val _task = getWellnessTask().toMutableStateList()
    val task: List<WellnessTask> = _task

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
        _task.find { it.id == item.id }?.let {task ->
            task.checked = checked
        }

    fun remove(item: WellnessTask){
        _task.remove(item)
    }
    private fun getWellnessTask() = List(30){
        WellnessTask(it, "Task # $it")
    }
}