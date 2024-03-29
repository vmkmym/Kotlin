package com.example.mytextrecognition

import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytextrecognition.ui.theme.MyTextRecognitionTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.sp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.digitalink.Ink
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import java.io.IOException
import java.util.Locale

// firebaseAnalytics 객체 선언하기

class MainActivity : ComponentActivity() {
    // firebaseAnalytics 변수 선언
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        setContent {
            MyTextRecognitionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TextRecognitionScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextRecognitionScreen() {
    var inputText by remember { mutableStateOf("") }
    val translatedTextState = remember { mutableStateOf("") }
    
    var enabledKoTranslator by remember { mutableStateOf(false) }
    var enabledJaTranslator by remember { mutableStateOf(false) }
    var enabledZhTranslator by remember { mutableStateOf(false) }
    var enabledDeTranslator by remember { mutableStateOf(false) }
    
    val koTranslator = remember { createTranslator(target = TranslateLanguage.KOREAN) }
    val jaTranslator = remember { createTranslator(target = TranslateLanguage.JAPANESE) }
    val zhTranslator = remember { createTranslator(target = TranslateLanguage.CHINESE) }
    val deTranslator = remember { createTranslator(target = TranslateLanguage.GERMAN) }
    
    LaunchedEffect(Unit) {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        
        koTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                enabledKoTranslator = true
            }
            .addOnFailureListener {}
        
        jaTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                enabledJaTranslator = true
            }
            .addOnFailureListener {}
        
        zhTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                enabledZhTranslator = true
            }
            .addOnFailureListener {}
        
        deTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                enabledDeTranslator = true
            }
            .addOnFailureListener {}
    }
    // 번역기
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text(text = "번역할 문장을 적어주세요!") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = translatedTextState.value,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                TransButton(
                    myTranslator = koTranslator,
                    inputText = inputText,
                    onTranslationComplete = { translatedText ->
                        translatedTextState.value = translatedText
                    },
                    enabled = enabledKoTranslator,
                    buttonText = "한국어"
                )
            }
            item {
                TransButton(
                    myTranslator = jaTranslator,
                    inputText = inputText,
                    onTranslationComplete = { translatedText ->
                        translatedTextState.value = translatedText
                    },
                    enabled = enabledJaTranslator,
                    buttonText = "일본어"
                )
            }
            item {
                TransButton(
                    myTranslator = zhTranslator,
                    inputText = inputText,
                    onTranslationComplete = { translatedText ->
                        translatedTextState.value = translatedText
                    },
                    enabled = enabledZhTranslator,
                    buttonText = "중국어"
                )
            }
            item {
                TransButton(
                    myTranslator = deTranslator,
                    inputText = inputText,
                    onTranslationComplete = { translatedText ->
                        translatedTextState.value = translatedText
                    },
                    enabled = enabledDeTranslator,
                    buttonText = "독일어"
                )
            }
        }
        MyGallery()
    }
}

@Composable
fun MyGallery() {
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedUri = uri }
    )
    var textRecognized by remember { mutableStateOf("") }
    var image: InputImage
    val clipboardManager = LocalClipboardManager.current
    // 이미지에서 텍스트 인식, 그리고 합쳐서 추출하는 작업
    Column {
        selectedUri?.let {
            val context = LocalContext.current
            try {
                image = InputImage.fromFilePath(context, selectedUri!!)
                val recognizer =
                    TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
                ​
                val result = recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        val combinedText = StringBuilder()
                        for (block in visionText.textBlocks) {
                            for (line in block.lines) {
                                for (element in line.elements) {
                                    val elementText = element.text
                                    combinedText.append(elementText).append(" ")
                                }
                            }
                        }
                        val finalText = combinedText.toString().trim()
                        textRecognized = finalText
                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    // 음성인식을 처리하는 부분
    // 음성인식 결과 반환 시 작동할 로직
    var voicetext by remember { mutableStateOf("") }
    val launcherSpeech = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val data = result.data
            // 음성인식 결과를 가져오는 코드
            val textResults = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if(!textResults.isNullOrEmpty()) {
                // 음성인식을 했을 때 음성이 여러 개 일 경우 리스트의 첫 번째 음성을 저장하기
                voicetext = textResults[0]
            }
        }
    )
    // 버튼들
    Column {
        // 이미지 텍스트 인식 버튼
        Button(
            onClick = {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            modifier = Modifier
                .padding(top = 13.dp)
                .fillMaxWidth()
        ) {
            Text(text = "이미지에서 텍스트 가져오기", fontSize = 15.sp)
        }
        
        // 이미지에서 추출한 텍스트 "복사" 버튼
        Button(
            onClick = {
                val annotatedText = AnnotatedString(textRecognized)
                clipboardManager.setText(annotatedText)
            },
            modifier = Modifier
                .padding(top = 13.dp)
                .fillMaxWidth()
        ) {
            Text(text = "이미지 텍스트 복사하기", fontSize = 15.sp)
        }
        
        // 음성 인식 버튼
        Button(
            onClick = {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "us-US")
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                launcherSpeech.launch(intent)
            },
            modifier = Modifier
                .padding(top = 13.dp)
                .fillMaxWidth()
        ) {
            Text(text = "음성 인식", fontSize = 15.sp)
        }
        
        // 음성에서 추출한 텍스트를 클립보드에 복사
        Button(
            onClick = {
                val annotatedVoice = AnnotatedString(voicetext)
                clipboardManager.setText(annotatedVoice)
            },
            modifier = Modifier
                .padding(top = 13.dp)
                .fillMaxWidth()
        ) { 
            Text(text = "음성인식 텍스트 복사하기", fontSize = 15.sp) 
        }
        
        // 이미지에서 추출된 텍스트 표시, 추출된 음성을 텍스트로 표시
        Text(text = "이미지 인식 결과 : ${textRecognized}", modifier = Modifier.padding(top = 16.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "음성 인식 결과 : ${voicetext}", modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
private fun TransButton(
    myTranslator: Translator,
    inputText: String,
    onTranslationComplete: (String) -> Unit,
    enabled: Boolean,
    buttonText: String
) {
    Button(
        onClick = {
            myTranslator.translate(inputText)
                .addOnSuccessListener { text ->
                    onTranslationComplete(text)
                }
        },
        modifier = Modifier
            .height(48.dp)
            .padding(top = 16.dp),
        enabled = enabled
    ) {
        Text(text = buttonText, color = Color.White)
    }
}

private fun createTranslator(target: String): Translator {
    val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(target)
        .build()
    return Translation.getClient(options)
}


@Composable
fun DegitalInkRecognition() {
    var inkBuilder = Ink.builder()
    lateinit var strokeBuilder: Ink.Stroke.Builder
    ​
    // 새 이벤트가 있을 때마다 호출합니다.
    fun addNewTouchEvent(event: MotionEvent) {
        val action = event.actionMasked
        val x = event.x
        val y = event.y
        var t = System.currentTimeMillis()
        ​
        //설정에서 타이밍 정보를 제공하지 않는 경우 Ink.Point.create 호출에서 세 번째 매개 변수(t)를 생략할 수 있습니다
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                strokeBuilder = Ink.Stroke.builder()
                strokeBuilder.addPoint(Ink.Point.create(x, y, t))
            }
                ​
                MotionEvent.ACTION_MOVE -> strokeBuilder!!.addPoint(Ink.Point.create(x, y, t))
            MotionEvent.ACTION_UP -> {
                strokeBuilder.addPoint(Ink.Point.create(x, y, t))
                inkBuilder.addStroke(strokeBuilder.build())
            }
                ​
            else -> {
                // 잉크 구성과 관련이 없는 작업
            }
        }
    }
// recognizer에 보낼 내용
    val ink = inkBuilder.build()
}

@Preview(showBackground = true)
@Composable
fun TextRecognitionPreview() {
    MyTextRecognitionTheme {
        TextRecognitionScreen()
    }
}
