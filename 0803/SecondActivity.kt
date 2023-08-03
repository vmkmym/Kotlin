package com.example.roominfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myInfo: String = intent.getStringExtra("") ?: ""
        setContent {
            openSecondActivity(myInfo)
        }
    }
}

@Composable
fun openSecondActivity(MyInfo: String) {
    Column {
        var modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        var verticalArrangement = Arrangement.Center,
        var horizontalAlignment = Alignment.CenterHorizontally
        )
        { Text(text = myInfo) }
    }
}