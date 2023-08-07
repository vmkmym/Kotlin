package com.example.roomsecond

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsecond.data.AppDatabase
import com.example.roomsecond.data.User
import com.example.roomsecond.ui.theme.RoomSecondTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var userNameState by remember { mutableStateOf(TextFieldValue()) }
    var userNumberState by remember { mutableStateOf(TextFieldValue()) }
    var userEmailState by remember { mutableStateOf(TextFieldValue()) }
    var userAgeState by remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current
    val db = remember {
        AppDatabase.getDatabase(context)
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val usersList by db.userDao().getAll().collectAsState(initial = emptyList())

        LazyColumn {
            items(usersList) { user ->
                UserItem(user = user)
            }
        }

        OutlinedTextField(
            value = userNameState,
            onValueChange = { userNameState = it },
            label = { Text("이름을 입력하세요") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color(0xFFE2993F)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = userNumberState,
            onValueChange = { userNumberState = it },
            label = { Text("전화번호를 입력하세요") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color(0xFF196CD3)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = userEmailState,
            onValueChange = { userEmailState = it },
            label = { Text("이메일을 입력하세요") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color(0xFFE242D2)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = userAgeState,
            onValueChange = { userAgeState = it },
            label = { Text("나이를 입력하세요") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color(0xFFCDDC39)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val userName = userNameState.text
                val userNumber: String = userNumberState.text
                val userEmail = userEmailState.text
                val userAge: String = userAgeState.text

                if (userName.isNotBlank()) {
                    val intent = Intent(context, UserDetailsActivity::class.java)
                    intent.putExtra("name", userName)
                    intent.putExtra("phone", userNumber)
                    intent.putExtra("email", userEmail)
                    intent.putExtra("age", userAge)
                    context.startActivity(intent)

                    val newUser =
                        User(name = userName, phone = userNumber, email = userEmail, age = userAge)
                    scope.launch(Dispatchers.IO) {
                        db.userDao().insertAll(newUser)
                    }
                }
            },
            enabled = userNameState.text.isNotBlank(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
        ) {
            Text(text = "등록", fontSize = 20.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun UserItem(user: User) {
    Column {
        Text(
            text = "이름: ${user.name}, 연락처: ${user.phone}, 이메일: ${user.email}, 나이: ${user.age}",
            fontSize = 10.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoomSecondTheme {
        MainScreen()
    }
}