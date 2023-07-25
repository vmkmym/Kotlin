package com.example.personality16

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personality16.ui.theme.Personality16Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Personality16Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MbtiList()
                }
            }
        }
    }
}

data class MbtiType(val type: String, val description: String, val imageResId: Int)

private val h5: TextStyle
    get() = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 30.sp
    )

private val caption: TextStyle
    get() = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp
    )

private val subtitle1: TextStyle
    get() = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp
    )

val mbtiTypes = listOf(
    MbtiType("ISTJ", "근면성실하고 철저한 관리자", R.drawable.istj),
    MbtiType("ISFJ", "용감하고 신중한 수호자", R.drawable.isfj),
    MbtiType("INFJ", "선의의 옹호자", R.drawable.infj),
    MbtiType("INTJ", "용의주도한 전략가", R.drawable.intj),
    MbtiType("ISTP", "만능 재주꾼", R.drawable.istp),
    MbtiType("ISFP", "호기심 많고 예술적인 예술가", R.drawable.isfp),
    MbtiType("INFP", "이상적인 중재자", R.drawable.infp),
    MbtiType("INTP", "논리적인 사색가", R.drawable.intp),
    MbtiType("ESTP", "모험적인 사업가", R.drawable.estp),
    MbtiType("ESFP", "자유로운 영혼의 연예인", R.drawable.esfp),
    MbtiType("ENFP", "재기발랄한 활동가", R.drawable.enfp),
    MbtiType("ENTP", "기민한 발명가", R.drawable.entp),
    MbtiType("ESTJ", "엄격하고 사실적인 관리자", R.drawable.estj),
    MbtiType("ESFJ", "사교적인 외교관", R.drawable.esfj),
    MbtiType("ENFJ", "정의로운 사회운동가", R.drawable.enfj),
    MbtiType("ENTJ", "대담한 통솔자", R.drawable.entj)
)

@Composable
fun MbtiTypeCard(mbtiType: MbtiType) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(8.dp))
            .shadow(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = mbtiType.imageResId),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = mbtiType.type,
                style = subtitle1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mbtiType.description,
                style = caption,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun MbtiList() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFF2F2F2))
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "MBTI 성격 유형",
            style = h5,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        for (mbtiType in mbtiTypes) {
            MbtiTypeCard(mbtiType = mbtiType)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
fun MbtiApp() {
    Surface(color = Color.White) {
        MbtiList()
    }
}
