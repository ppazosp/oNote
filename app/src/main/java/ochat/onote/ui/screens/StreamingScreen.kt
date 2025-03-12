package ochat.onote.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ochat.onote.R
import ochat.onote.ui.theme.MontserratFontFamily
import ochat.onote.ui.theme.ONoteTheme
import ochat.onote.ui.theme.USColor
import kotlin.math.abs

@Preview
@Composable
fun StreamingPreview(){
    ONoteTheme {
        StreamingScreen(false)
    }
}

@Composable
fun StreamingScreen(isOnline: Boolean) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(Color.White)
    }

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedFileUri = uri
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { filePickerLauncher.launch("*/*") }, // Open file picker
                containerColor = USColor,
                shape = CircleShape,
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.attach),
                    tint = Color.White,
                    contentDescription = "Attach",

                    modifier = Modifier
                        .size(32.dp)
                )
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
            StreamingView(isOnline)
        }
    }
}

@Composable
fun StreamingView(isOnline: Boolean) {
    var isExpanded by remember { mutableStateOf(false) }

    val weight by animateFloatAsState(
        targetValue = if (isExpanded) 0.5f else 0.15f,
        animationSpec = tween(durationMillis = 500),
        label = "Attachment Expansion"
    )

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
            TranscriptionView(isOnline)
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
            AttachmentsView()
        }
    }
}

fun getLine(): String{
    return "This text is being rendered in real time, I promise"
}

@Composable
fun TranscriptionView(isOnline: Boolean) {
    val transcriptionLines = remember { mutableStateListOf<String>() }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    if (isOnline) {
        LaunchedEffect(Unit) {
            while (true) {
                val newLine = getLine()
                if (newLine.isNotEmpty()) {
                    transcriptionLines.add(newLine)
                    coroutineScope.launch {
                        listState.scrollToItem(transcriptionLines.size - 1)
                    }
                }
                delay(1000)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box {
            Text(
                text = "TRANSCRIPCIÓN",
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
            items(transcriptionLines) { line ->
                Text(
                    text = line,
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

@Composable
fun AttachmentsView(){

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
            items(20) { index ->
                AttachmentsItem(index)
            }
        }
    }
}

@Composable
fun AttachmentsItem(index: Int){
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
            text = "Documento$index.pdf",
            fontFamily = MontserratFontFamily,
            fontStyle = FontStyle.Italic,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = USColor,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis
        )

    }
}