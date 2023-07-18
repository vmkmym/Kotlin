package com.example.subject0717

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.subject0717.ui.theme.Subject0717Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Subject0717Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
    }
}

@Composable
fun GreetingPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text elements
        Text(
            text = "저의 MBTI는 INTJ입니다!",
            color = Color.Magenta,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "나무보다는 숲을 보려는 성향이 있다",
        )
        Text(
            text = "영혼 없는 리액션을 하는 편이다",
        )

        // Image element
        val imagePainter: Painter = painterResource(id = R.drawable.intj)
        androidx.compose.foundation.Image(
            painter = imagePainter,
            contentDescription = "Intj Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .aspectRatio(1f)
        )
    }
}

// @Preview
@Composable
fun GreetingPreviewWithImage() {
    GreetingPreview()
}

// @Preview(showBackground = true)
@Composable
fun Practice() {
    Subject0717Theme() {
        Column {
            MyButton("Button1")
            MyButton("Button2")
            MyButton("Button3")
        }
    }
}

@Composable
fun MyButton(name: String) {
    var count by remember { mutableStateOf(0) }
    Button(onClick = {
        if (count < 10) {
            count += 2
        }
    }) {
        Text(text = "${name}, ${count}", fontSize = 16.sp, color = Color.Yellow)
    }
}

@Composable
fun MyCounter() {
    var count by remember { mutableStateOf(0) }
    Button(
        onClick = { count++ },
        enabled = count <= 10
    ) {
        when {
            count > 10 -> Text("감사합니다^^")
            count in 6..10 -> Text("조금만 더 $count")
            count in 1..5 -> Text("$count")
            count == 0 -> Text("눌러보세요")
        }
    }
}

@Preview
@Composable
fun PreviewMyCounter() {
    MyCounter()
}

@Composable
fun PresentationCountdown() {
    var count by remember { mutableStateOf(10) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = {
                if (count > 0) {
                    count--
                }
            },
            enabled = count >= 1
        ) {
            Text(
                text = when {
                    count > 0 -> "발표 종료 까지 남은 시간 : $count"
                    count == 0 -> "1조 발표 끝! 감사합니다^^"
                    else -> "발표까지 남은 시간: $count"
                }
            )
        }
    }
}

//@Preview
@Composable
fun PreviewPresentationCountdown() {
    PresentationCountdown()
}
