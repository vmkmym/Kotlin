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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.content.edit
import java.util.Date

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
    // 카운트 값을 저장하고 가져오기 위한 코드
    val activity = LocalContext.current as? Activity
    val sharedPref = remember { activity?.getPreferences(Context.MODE_PRIVATE) }
    var count by remember {
        val countValue = sharedPref?.getInt("counter", 0) ?: 0
        mutableStateOf(countValue.toLong())
    }

    //  누적 클릭 횟수를 저장하고 가져오기 위한 코드
    var totalClickCount by remember {
        val totalCountValue = sharedPref?.getInt("totalClickCount", 0) ?: 0
        mutableStateOf(totalCountValue)
    }

    //  마지막버튼클릭시간 값을 저장하고 가져오기 위한 코드
    var lastButtonClickTime by remember {
        val lastTime = sharedPref?.getLong("lastButtonClickTime", 0L) ?: 0L
        mutableStateOf(if (lastTime == 0L) Date().time else lastTime)
    }

    // 버튼 클릭 시간을 저장하는 함수
    fun saveTime() {
        lastButtonClickTime = Date().time
        sharedPref?.edit {
            putLong("lastButtonClickTime", lastButtonClickTime)
        }
    }

    // 누적 클릭 횟수를 저장하는 함수
    fun saveClickCount() {
        totalClickCount++
        sharedPref?.edit {
            putInt("totalClickCount", totalClickCount)
            putInt("counter", count.toInt())

        }
    }

    // 앱이 시작될 때 누적 클릭 횟수를 불러옴
    LaunchedEffect(Unit) {
        totalClickCount = sharedPref?.getInt("totalClickCount", 0) ?: 0
        count = sharedPref?.getInt("counter", 0)?.toLong() ?: 0
    }

    // 여기서부터는 버튼 구현
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

            // 2배 증감 버튼과 시간 기록
            ButtonGroupRow(
                buttonDataList = listOf(
                    ButtonData(text = "X2", color = Color.Green) {
                        count *= 2
                        saveTime()
                        saveClickCount() // 누적 클릭 횟수 저장
                    },
                    ButtonData(text = "/2", color = Color.Yellow) {
                        count /= 2
                        saveTime()
                        saveClickCount() // 누적 클릭 횟수 저장
                    }
                )
            )

            // 증감 버튼과 시간 기록
            ButtonGroupRow(
                buttonDataList = listOf(
                    ButtonData(text = "+", color = Color.White) {
                        count++
                        saveTime()
                        saveClickCount() // 누적 클릭 횟수 저장
                    },
                    ButtonData(text = "-", color = Color.White) {
                        count--
                        saveTime()
                        saveClickCount() // 누적 클릭 횟수 저장
                    }
                )
            )

            // 초기화 버튼과 시간 기록
            ButtonGroupRow(
                buttonDataList = listOf(
                    ButtonData(text = "0", color = Color.Magenta) {
                        count = 0
                        saveTime()
                        saveClickCount() // 누적 클릭 횟수 저장
                    }
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { saveTime() },
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "LastButtonClickTime: ${Date(lastButtonClickTime)}",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

// 함수로 반복된 부분 빼기
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

// 버튼 함수 빼기
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
