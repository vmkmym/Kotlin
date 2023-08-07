package com.example.roomsecond

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsecond.data.AppDatabase
import com.example.roomsecond.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)

        setContent {
            UserDetailsScreenActivity(db)
        }
    }
}

val AppBarHeight = 56.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreenActivity(db: AppDatabase) {
    val userList by db.userDao().getAll().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Box {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "연락처 리스트") }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = AppBarHeight)
            ) {
                items(userList) { user ->
                    UserCard(user, onDelete = {
                        scope.launch(Dispatchers.IO) {
                            db.userDao().delete(user)
                        }
                    }, onUpdate = { name, phone, email, age ->
                        scope.launch(Dispatchers.IO) {
                            user.name = name
                            user.phone = phone
                            user.email = email
                            user.age = age.toString()
                            db.userDao().update(user)
                        }
                    })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(user: User, onDelete: () -> Unit, onUpdate: (String, String, String, Int) -> Unit) {
    // 수정할 값을 담을 상태 변수들
    var name by remember { mutableStateOf(user.name) }
    var phone by remember { mutableStateOf(user.phone) }
    var email by remember { mutableStateOf(user.email) }
    var age by remember { mutableStateOf(user.age.toString()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = name.toString(),
                onValueChange = { name = it },
                label = { Text("이름") }
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = phone.toString(),
                onValueChange = { phone = it },
                label = { Text("전화번호") }
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = email.toString(),
                onValueChange = { email = it },
                label = { Text("이메일") }
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("나이") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onDelete,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                ) {
                    Text(text = "Delete", fontSize = 15.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    onClick = {
                        onUpdate(
                            name.toString(),
                            phone.toString(),
                            email.toString(),
                            age.toInt()
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                ) {
                    Text(text = "Update", fontSize = 15.sp, color = Color.White)
                }
            }
        }
    }
}
