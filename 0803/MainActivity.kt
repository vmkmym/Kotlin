@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.roominfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.roominfo.ui.theme.RoomInfoTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.roominfo.data.AppDatabase
import com.example.roominfo.data.MIGRATION_1_2
import com.example.roominfo.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomInfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    // 이름 필드
    var userNameState by remember { mutableStateOf(TextFieldValue()) }
    var showName by remember { mutableStateOf("") }
    // 전화번호 필드
    var userNumberState by remember { mutableStateOf(TextFieldValue()) }
    var showNumber by remember { mutableStateOf("") }
    // 이메일 필드
    var userEmailState by remember { mutableStateOf(TextFieldValue()) }
    var showEmail by remember { mutableStateOf("") }
    // 보여지게 하기
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val db = remember {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "contacts.db"
                // 빌드 하기 전에 마이그레이션하기
            ).addMigrations(MIGRATION_1_2).build()
        }
        val scope = rememberCoroutineScope()
        // 이름
        OutlinedTextField(
            value = userNameState,
            onValueChange = { userNameState = it },
            label = { Text("이름을 입력하세요") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color(0xFF42c8e2)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        // 전화번호
        OutlinedTextField(
            value = userNumberState,
            onValueChange = { userNumberState = it },
            label = { Text("전화번호를 입력하세요") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color(0xFF42c8e2)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        // 이메일
        OutlinedTextField(
            value = userEmailState,
            onValueChange = { userEmailState = it },
            label = { Text("이메일을 입력하세요") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color(0xFF42c8e2)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        // 등록 버튼
        Button(
            onClick = {
                val userName = userNameState.text
                val userNumber = userNumberState.text
                val userEmail = userEmailState.text

                if (userName.isNotBlank()) {
                    showName = userName
                    showNumber = userNumber
                    showEmail = userEmail
                    showToast(context, "이름: $userName, 연락처: $userNumber, 이메일: $userEmail")
                }
                // newUser 생성, db 가져오기
                val newUser = User(name = userName, phone = userNumber, email = userEmail)
                scope.launch(Dispatchers.IO) {
                    db.userDao().insertAll(newUser)
                }
            },
            enabled = userNameState.text.isNotBlank(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
        ) {
            Text(text = "등록", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (showName.isNotEmpty() && showEmail.isNotEmpty() && showNumber.isNotEmpty()) {
            Text(
                text = "이름: $showName, 연락처: $showNumber, 이메일: $showEmail",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

fun showToast(context: android.content.Context, message: String) {
    android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    RoomInfoTheme {
        MainScreen()
    }
}