package com.example.kotlinstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlinstudy.ui.theme.KotlinStudyTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinStudyTheme {
                StarbucksMenuRecommendation()
            }
        }
    }
}

data class Menu(
    val name: String,
    val englishName: String,
    val imageResId: Int,
    private val price: Int,
    val labelType: LabelType = LabelType.None
) {
    val displayPrice: String
        get() {
            val dcFormat = DecimalFormat("#,###")
            return "${dcFormat.format(price)}원"
        }
    enum class LabelType {
        None, Best, New
    }
}


@Composable(labelType: Menu.LabelType) {
    when (labelType) {
        Menu.LabelType.Best -> Text(
            text = "Best",
            fontStyle = FontStyle.Italic,
            fontSize = 8.sp,
            color = Color.Red
        )
        Menu.LabelType.New -> Text(
            text = "New",
            fontStyle = FontStyle.Italic,
            fontSize = 8.sp,
            color = Color.Green
        )
        else -> Unit
    }
}


@Composable
fun StarbucksMenuRecommendation() {
    val coffeeList = listOf(
        Menu("아이스 카페 아메리카노", "Iced Cafe Americano", R.drawable.iced_cafe_americano, "4,500원", labelType = Menu.LabelType.Best,),
        Menu("아이스 카페 라떼", "Iced Cafe Latte", R.drawable.iced_cafe_latte, "5,000원", labelType = Menu.LabelType.Best,),
        Menu(
            "씨솔트 카라멜 콜드 브루",
            "Sea Salt Caramel Cold Brew",
            R.drawable.sea_salt_caramel_cold_brew,
            "6,300원",
            labelType = Menu.LabelType.New,
        ),
        Menu(
            "스트로베리 초코 크림 프라푸치노",
            "Strawberry Choco Cream Frappuccino",
            R.drawable.blackpink,
            "5,700원",
            labelType = Menu.LabelType.Best,
        ),
        Menu("콜드 브루", "Cold Brew", R.drawable.cold_brew, "6,900원",labelType = Menu.LabelType.New,),
        Menu("제주 비자림 콜드 브루", "Jeju Forest Cold Brew", R.drawable.jeju, "4,500원"),
        Menu("아이스 카페 라떼", "Iced Cafe Latte", R.drawable.iced_cafe_latte, "5,000원"),
        Menu(
            "씨솔트 카라멜 콜드 브루",
            "Sea Salt Caramel Cold Brew",
            R.drawable.sea_salt_caramel_cold_brew,
            "6,300원"
        ),
        Menu(
            "스트로베리 초코크림 프라푸치노",
            "Strawberry Choco Cream Frappuccino",
            R.drawable.blackpink,
            "5,700원"
        ),
        Menu("콜드 브루", "Cold Brew", R.drawable.cold_brew, "6,900원"),
        Menu("제주 비자림 콜드 브루", "Jeju Forest Cold Brew", R.drawable.jeju, "4,500원"),
    )

    LazyColumn {
        items(coffeeList) { menu ->
            MenuItem(menu)
        }
    }
}


@Composable
fun MenuItem(menu: Menu) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {}),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = menu.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(text = menu.name, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Text(text = menu.englishName, fontSize = 10.sp, color = Color.LightGray)
                Text(
                    text = menu.price,
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// 수정 중
