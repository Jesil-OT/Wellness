package com.example.wellness

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wellness.ui.theme.WellnessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WellnessTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    WellnessScreen()
                }
            }
        }
    }
}

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTaskList(
            list = wellnessViewModel.task,
            onClose = { task -> wellnessViewModel.remove(task) },
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            }
        )
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableIntStateOf(0) }
    StatelessCounter(
        count = count,
        onIncrement = { count++ },
        onResetCount = { count = 0 },
        modifier = modifier
    )
}

@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    onResetCount: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(20.dp)
    ) {
        if (count > 0) {
            Text(
                modifier = modifier.padding(top = 10.dp),
                text = "You've had $count glasses."
            )
        }
        Row(modifier.padding(top = 10.dp)) {
            Button(
                onClick = onIncrement,
                enabled = count < 10
            ) {
                Text(text = "Add One")
            }
        }
    }

}

@Composable
fun WellnessTaskList(
    modifier: Modifier = Modifier,
    onClose: (WellnessTask) -> Unit,
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    list: List<WellnessTask>
) {
    LazyColumn(modifier = modifier.padding(10.dp)) {
        items(
            items = list,
            key = { task -> task.id }
        ) { wellnessTask ->
            WellnessTaskItem(
                taskName = wellnessTask.label,
                checkedState = wellnessTask.checked,
                onClose = { onClose(wellnessTask) },
                onCheckedChanged = { checked -> onCheckedTask(wellnessTask, checked) }
            )
        }
    }

}

@Composable
fun WellnessTaskItem(
    taskName: String,
    checkedState: Boolean,
    onClose: () -> Unit,
    onCheckedChanged: (Boolean) -> Unit
) {
    //var checkState by rememberSaveable { mutableStateOf(false) }
    WellnessTaskItem(
        checked = checkedState,
        taskName = taskName,
        onCheckedChanged = onCheckedChanged,
        onClose = onClose
    )
}


@Composable
fun WellnessTaskItem(
    modifier: Modifier = Modifier,
    checked: Boolean,
    taskName: String,
    onCheckedChanged: (Boolean) -> Unit,
    onClose: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 5.dp,
        tonalElevation = 5.dp,
        shape = RoundedCornerShape(6.dp),
        color = Color.LightGray
    ) {
        Row(
            modifier = modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                text = taskName
            )
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChanged
            )
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Close")
            }
        }
    }
    Spacer(modifier = modifier.height(10.dp))
}

@Preview(showBackground = true, widthDp = 500, heightDp = 300)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 500, heightDp = 300
)
@Composable
fun GreetingPreview() {
    WellnessTheme {
        Column {
            WellnessScreen()
        }
    }
}

@Preview
@Composable
fun WellTaskItemPreview() {
    WellnessTheme {
        WellnessTaskItem(
            taskName = "Have you taken your 15 minute walk today?",
            checked = true,
            onClose = {},
            onCheckedChanged = {}
        )
    }
}

@Preview
@Composable
fun WellnessTaskListPreview() {
    val wellnessViewModel: WellnessViewModel = viewModel()
    WellnessTheme {
        WellnessTaskList(
            list = wellnessViewModel.task,
            onClose = {},
            onCheckedTask = { _, _ -> }
        )
    }
}