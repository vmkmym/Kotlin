package com.example.countup

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.countup.ui.theme.CountUpTheme

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
    CountUpTheme {
        // Create a mutable state object to hold the count value
        val count = remember { mutableStateOf(0L) } // 오버플로우 해결하기 (Int64사용)
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 누적 클릭 횟수 기억하는 텍스트 (앱 껐다 켜도 숫자 그대로)
                Text(
                    text = count.value.toString(),
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
                        ButtonData(text = "X2", color = Color.Green, onClick = { count.value *= 2 }),
                        ButtonData(text = "/2", color = Color.Yellow, onClick = { count.value /= 2 })
                    )
                )

                // 감소와 감소 버튼 그룹
                ButtonGroupRow(
                    buttonDataList = listOf(
                        ButtonData(text = "+", color = Color.White, onClick = { count.value++ }),
                        ButtonData(text = "-", color = Color.White, onClick = { count.value-- })
                    )
                )

                // 초기화 버튼
                ButtonGroupRow(
                    buttonDataList = listOf(
                        ButtonData(text = "0", color = Color.Magenta, onClick = { count.value = 0L })
                    )
                )
            }
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
                color = buttonData.color,
                onClick = buttonData.onClick
            )
        }
    }
}

data class ButtonData(val text: String, val color: Color, val onClick: () -> Unit)



@Composable
fun CalculatorButton(text: String, color: Color, onClick: () -> Unit) {
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

@Composable
fun calculateFontSize(number: Long): TextUnit {
    // 숫자의 길이에 따라 동적으로 fontSize 계산
    val maxLength = 10 // 표시할 수 있는 최대 자릿수
    val baseFontSize = 70.sp // 기본 폰트 크기
    val maxFontSize = 50.sp // 최대 폰트 크기
    val minFontSize = 30.sp // 최소 폰트 크기

    val length = number.toString().length
    val fontSize = (baseFontSize.value - (length - maxLength) * 5).coerceIn(
        minFontSize.value.toInt().toFloat(),
        maxFontSize.value.toInt().toFloat()
    ).sp
    return fontSize
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreetingApp()
}
