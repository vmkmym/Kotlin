package com.example.mygallery

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mygallery.ui.theme.MyGalleryTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainDriveActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyGalleryTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }
        }
    }
}

// 이런 식으로 함수로 빼내기 (Ctrl+W : 중괄호 블록 선택)
@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MyDrive()
    }
}

@Composable
fun MyDrive() {
//    val uris by remember { mutableStateListOf<Uri?>() }
    var selectedUris by remember { mutableStateOf<List<Uri?>>(emptyList()) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            selectedUris = uris
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedUris.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.cup),
                contentDescription = "이미지 예시",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
                    .shadow(2.dp)
            )
        } else {
            LazyRow {
                items(selectedUris.size) { index ->
                    val uri = selectedUris[index]
                    uri?.let {
                        val context = LocalContext.current
                        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ImageDecoder.decodeBitmap(
                                ImageDecoder.createSource(
                                    context.contentResolver,
                                    uri
                                )
                            )
                        } else {
                            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                        }

                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "이것은 비트맵 이미지",
                            modifier = Modifier
                                .padding(4.dp)
                                .size(200.dp)
                                .shadow(2.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
        ) {
            Text(text = "Image Change Button", fontSize = 15.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DrivePreview() {
    MyGalleryTheme {
        MyDrive()
    }
}
