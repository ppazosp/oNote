package ochat.onote.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ochat.onote.data.UISubject
import ochat.onote.data.getSubjects
import ochat.onote.ui.theme.MontserratFontFamily
import ochat.onote.ui.theme.ONoteTheme
import ochat.onote.ui.theme.USColor

@Preview
@Composable
fun GridPreview(){
    ONoteTheme {
        GridScreen {  }
    }
}

@Composable
fun GridScreen(onStartNavGraph: (subjectName: String) -> Unit) {

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setNavigationBarColor(Color.White)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            GridView(onStartNavGraph)
        }
    }
}


@Composable
fun GridView(onStartNavGraph: (subjectName: String) -> Unit) {
    val gridState = rememberLazyGridState()
    val context = LocalContext.current

    var items by remember { mutableStateOf<List<UISubject>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        items = withContext(Dispatchers.IO) {
            getSubjects(context)
        }
        isLoading = false
    }

    if (isLoading) {
        LoadingScreen()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Text(
            text = "ASIGNATURAS",
            fontFamily = MontserratFontFamily,
            fontStyle = FontStyle.Normal,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = USColor
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            state = gridState,
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items.size) { index ->
                GridItem(onStartNavGraph, items[index])
            }
        }

    }
}

@Composable
fun GridItem(onStartNavGraph: (subjectName: String) -> Unit, subject: UISubject) {
    Box(
        modifier = Modifier
            .size(192.dp)
            .border(2.dp, USColor)
            .background(
                color = Color.White
            )
            .clickable { onStartNavGraph(subject.name) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.End
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(USColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = subject.banner,
                    contentDescription = "Subject banner",

                    modifier = Modifier
                        .padding(16.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.White),
                contentAlignment = Alignment.CenterEnd
            ){
                Text(
                    text = subject.name,
                    fontFamily = MontserratFontFamily,
                    fontStyle = FontStyle.Normal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = USColor,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}