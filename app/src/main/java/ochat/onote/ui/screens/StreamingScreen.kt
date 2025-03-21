package ochat.onote.ui.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.ktor.http.cio.parseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import ochat.onote.R
import ochat.onote.backend.DriveFile
import ochat.onote.backend.TranscriptionViewModel
import ochat.onote.backend.chat.getDriveFile
import ochat.onote.backend.chat.uploadFile
import ochat.onote.data.UIFilesSimple
import ochat.onote.data.UIStreamingClass
import ochat.onote.data.getStreamingClass
import ochat.onote.ui.theme.MontserratFontFamily
import ochat.onote.ui.theme.ONoteTheme
import ochat.onote.ui.theme.USColor
import ochat.onote.utils.downloadFile
import kotlin.math.abs

@Preview
@Composable
fun StreamingPreview(){
    ONoteTheme {
        StreamingScreen(false, "Lógica", "Clase 1")
    }
}

@Composable
fun StreamingScreen(isOnline: Boolean, subjectName: String, className: String,) {
    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(Color.White)
    }

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var driveFileToUpload by remember { mutableStateOf<DriveFile?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedFileUri = uri
        if (uri != null) {
            Toast.makeText(context, "File selected: ${uri.path}", Toast.LENGTH_SHORT).show()
            driveFileToUpload = getDriveFile(context, uri, subjectName, className)
        }
    }

    LaunchedEffect(driveFileToUpload) {
        driveFileToUpload?.let { driveFile ->
            isUploading = true
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Uploading file...", Toast.LENGTH_SHORT).show()
            }

            try {
                val response = withContext(Dispatchers.IO) {
                    uploadFile(driveFile)
                }

                withContext(Dispatchers.Main) {
                    if (response.isNullOrEmpty()) {
                        Toast.makeText(context, "Upload failed: No response from server", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            isUploading = false
            driveFileToUpload = null
        }
    }

    var showResume by remember { mutableStateOf<Boolean>(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Column(
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Space between buttons
            ) {
                FloatingActionButton(
                    onClick = { showResume = !showResume },
                    containerColor = USColor,
                    shape = CircleShape,
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(R.drawable.stars),
                        tint = Color.White,
                        contentDescription = "Camera",
                        modifier = Modifier.size(32.dp)
                    )
                }

                FloatingActionButton(
                    onClick = { filePickerLauncher.launch("*/*") },
                    containerColor = USColor,
                    shape = CircleShape,
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(R.drawable.attach),
                        tint = Color.White,
                        contentDescription = "Attach",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            StreamingView(isOnline, showResume, className, subjectName)
        }
    }
}

@Composable
fun StreamingView(isOnline: Boolean, showResume: Boolean, className: String, subjectName: String, ) {
    var isExpanded by remember { mutableStateOf(false) }

    val weight by animateFloatAsState(
        targetValue = if (isExpanded) 0.5f else 0.15f,
        animationSpec = tween(durationMillis = 500),
        label = "Attachment Expansion"
    )

    var streamingClass by remember { mutableStateOf<UIStreamingClass?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        streamingClass = withContext(Dispatchers.IO) {
            getStreamingClass(className, subjectName)
        }
        isLoading = false
    }

    if (isLoading) {
        LoadingScreen()
    }else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(2.dp, USColor)
                    .padding(16.dp)
            ) {
                TranscriptionView(isOnline, showResume, streamingClass!!.name, streamingClass!!.teacher, streamingClass!!.transcript, streamingClass!!.resume)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { _, dragAmount ->
                            if (abs(dragAmount) > 10) {
                                isExpanded = dragAmount < 0
                            }
                        }
                    }
                    .drawBehind {
                        drawLine(
                            USColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 2.dp.toPx()
                        )
                        drawLine(
                            USColor,
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                        drawLine(
                            USColor,
                            start = Offset(size.width, 0f),
                            end = Offset(size.width, size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                    .padding(16.dp)
            ) {
                AttachmentsView(streamingClass!!.files)
            }
        }
    }
}

@Composable
fun TranscriptionView(isOnline: Boolean, showResume: Boolean, className: String, teacherName: String, transcript: String, resume: String, viewModel: TranscriptionViewModel = remember { TranscriptionViewModel() }) {
    val listState = rememberLazyListState()
    val streamingTranscription by viewModel.transcriptionText.collectAsState("Waiting for transcription...")

    if (isOnline) {
        LaunchedEffect(Unit) {
            viewModel.startListening()
        }

        DisposableEffect(Unit) {
            onDispose {
                viewModel.stopListening()
            }
        }

        LaunchedEffect(streamingTranscription) {
            listState.animateScrollToItem(listState.layoutInfo.totalItemsCount)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box {
            Text(
                text = "${className.uppercase()} ~ $teacherName",
                fontFamily = MontserratFontFamily,
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = USColor
            )
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(0.dp),
        ) {
            item {
                AnimatedContent(
                    targetState = if (isOnline) streamingTranscription else if (showResume) resume else transcript,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(700)) togetherWith fadeOut(animationSpec = tween(700))
                    },
                    label = "Transcript Transition"
                )  { targetText ->
                    Text(
                        text = targetText,
                        fontFamily = MontserratFontFamily,
                        fontStyle = FontStyle.Normal,
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
            }
        }

    }
}

@Composable
fun AttachmentsView(files: List<UIFilesSimple>) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box {
            Text(
                text = "ADJUNTOS",
                fontFamily = MontserratFontFamily,
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = USColor
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(files.size) { index ->
                AttachmentsItem(files[index])
            }
        }
    }
}

@Composable
fun AttachmentsItem(file: UIFilesSimple){
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(R.drawable.doc),
            contentDescription = "Document",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(24.dp)
        )

        Text(
            text = file.name,
            fontFamily = MontserratFontFamily,
            fontStyle = FontStyle.Italic,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = USColor,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,

            modifier = Modifier
            .clickable {
                downloadFile(context, file.url, file.name, file.ext)
            },
        )

    }
}