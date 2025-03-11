package ochat.onote.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun ClassPreview(){
    ONoteTheme {
        ClassScreen({})
    }
}

@Composable
fun ClassScreen(onOpenStreaming: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        ClassView(onOpenStreaming)
    }
}

@Composable
fun ClassView(onOpenStreaming: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box {
            Text(
                text = "CLASES",
                fontFamily = MontserratFontFamily,
                fontStyle = FontStyle.Normal,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = USColor
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(64.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box {
                    Text(
                        text = "SIGUIENTE CLASE",
                        fontFamily = MontserratFontFamily,
                        fontStyle = FontStyle.Normal,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = USColor
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(2.dp, USColor)
                        .weight(0.5f)
                        .clickable { onOpenStreaming() }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            )
            {
                Box{
                    Text(
                        text = "CLASES ANTERIORES",
                        fontFamily = MontserratFontFamily,
                        fontStyle = FontStyle.Normal,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = USColor
                    )
                }

                LazyColumn(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(5) { index ->
                        ClassItem(index, onOpenStreaming)
                    }
                }
            }
        }
    }
}

@Composable
fun ClassItem(index: Int, onOpenStreaming: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .border(2.dp, USColor)
            .clickable { onOpenStreaming() }
    ) {
        Text(text = "Item ${index + 1}")
    }
}