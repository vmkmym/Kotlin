package com.example.countup

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingApp()
        }
    }
}

@Composable
fun GreetingApp() {
    val activity = LocalContext.current as? Activity
    val sharedPref = remember { activity?.getPreferences(Context.MODE_PRIVATE) }
    var count by remember {
        val countValue = sharedPref?.getInt("counter", 0) ?: 0
        mutableStateOf(countValue.toLong())
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                style = TextStyle(
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(20.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 2배 늘리기와 반으로 줄이기 버튼 그룹
            ButtonGroupRow(
                buttonDataList = listOf(
                    ButtonData(text = "X2", color = Color.Green) { count *= 2 },
                    ButtonData(text = "%2", color = Color.Yellow) { count /= 2 }
                )
            )

            // 감소와 감소 버튼 그룹
            ButtonGroupRow(
                buttonDataList = listOf(
                    ButtonData(text = "+", color = Color.White) { count++ },
                    ButtonData(text = "-", color = Color.White) { count-- }
                )
            )

            // 초기화 버튼
            ButtonGroupRow(
                buttonDataList = listOf(
                    ButtonData(text = "0", color = Color.Magenta) { count = 0L }
                )
            )
        }
    }
}

@Composable
fun ButtonGroupRow(buttonDataList: List<ButtonData>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for (buttonData in buttonDataList) {
            CalculatorButton(
                text = buttonData.text,
                onClick = buttonData.onClick
            )
        }
    }
}



data class ButtonData(val text: String, val color: Color, val onClick: () -> Unit)

@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(25.dp),
        shape = CircleShape,
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreetingApp()
}
