package ochat.onote.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GridView(onStartNavGraph)
        }
    }
}

@Composable
fun GridView(onStartNavGraph: (subjectName: String) -> Unit) {
    val items = List(20) { "ITEM ${it + 1}" } // Lista de 20 elementos
    val gridState = rememberLazyGridState()

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
            columns = GridCells.Fixed(2),
            state = gridState,
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Espaciado entre columnas
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items.size) { index ->
                GridItem(onStartNavGraph, items[index])
            }
        }

    }
}

@Composable
fun GridItem(onStartNavGraph: (subjectName: String) -> Unit, name: String) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = USColor
            )
            .clickable { onStartNavGraph(name) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            fontFamily = MontserratFontFamily,
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White
        )
    }
}