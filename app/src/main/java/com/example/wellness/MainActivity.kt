package com.example.wellness

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun WellnessScreen(modifier: Modifier = Modifier) {
    StatefulCounter(modifier)
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
    Surface(
        modifier = modifier.fillMaxSize()
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
                Button(
                    modifier = modifier.padding(start = 10.dp),
                    onClick = onResetCount
                ) {
                    Text(text = "Clear water count")
                }
            }
        }
    }

}


@Composable
fun WellnessTaskItem(
    modifier: Modifier = Modifier,
    taskName: String,
    onClose: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 16.dp,
        tonalElevation = 10.dp,
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
            IconButton(onClick = { onClose.invoke() }) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Close")
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun GreetingPreview() {
    WellnessTheme {
        WellnessScreen()
    }
}

@Preview
@Composable
fun WellTaskItemPreview() {
    WellnessTheme {
        WellnessTaskItem(taskName = "Have you taken your 15 minute walk today?") {}
    }
}