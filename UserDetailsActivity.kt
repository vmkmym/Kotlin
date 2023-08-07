package com.example.roomsecond

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreenActivity(db: AppDatabase) {
    val userList by db.userDao().getAll().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "연락처 리스트") }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(userList) { user ->
                UserCard(user, onDelete = {
                    scope.launch(Dispatchers.IO) {
                        db.userDao().delete(user)
                    }
                })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun UserCard(user: User, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(text = "이름: ${user.name}", fontSize = 15.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "전화번호: ${user.phone}", fontSize = 15.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "이메일: ${user.email}", fontSize = 15.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "나이: ${user.age}", fontSize = 15.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(5.dp))

            Button(
                onClick = onDelete,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
            ) {
                Text(text = "Delete", fontSize = 15.sp, color = Color.White)
            }
        }
    }
}

