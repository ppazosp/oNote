package ochat.onote.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ochat.onote.data.UIClass
import ochat.onote.ui.theme.MontserratFontFamily
import ochat.onote.ui.theme.ONoteTheme
import ochat.onote.ui.theme.USColor
import ochat.onote.utils.formatDate
import ochat.onote.utils.timeFormatter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Preview
@Composable
fun ClassPreview(){
    ONoteTheme {
        ClassScreen(listOf()) {}
    }
}

@Composable
fun ClassScreen(classItems: List<UIClass>, onOpenStreaming: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        ClassView(classItems, onOpenStreaming)
    }
}

@Composable
fun ClassView(classItems: List<UIClass>, onOpenStreaming: () -> Unit) {

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
                        .background(USColor)
                ){
                    NextClassItem(classItems[0])
                }
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
                    val filteredClassItems = classItems.drop(1)

                    items(filteredClassItems.size) { index ->
                        ClassItem(filteredClassItems[index], onOpenStreaming)
                    }
                }
            }
        }
    }
}

@Composable
fun NextClassItem(classs: UIClass) {

    val now = LocalDateTime.now()

    val isOnline = now.isAfter(classs.startDate) && now.isBefore(classs.endDate)

    Box{
        Text(
            text = if (isOnline) "• Online" else "• Offline",
            fontFamily = MontserratFontFamily,
            fontStyle = FontStyle.Normal,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isOnline) Color.Green else Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "${classs.name} ~ ${classs.teacher}",
            fontFamily = MontserratFontFamily,
            fontStyle = FontStyle.Normal,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "${classs.startDate.format(timeFormatter)} - ${classs.endDate.format(timeFormatter)}",
                fontFamily = MontserratFontFamily,
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )

            Text(
                text = classs.startDate.toLocalDate().formatDate(),
                fontFamily = MontserratFontFamily,
                fontStyle = FontStyle.Normal,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }
    }
}

@Composable
fun ClassItem(classs: UIClass, onOpenStreaming: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .border(2.dp, USColor)
            .clickable { onOpenStreaming() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "${classs.name} ~ ${classs.teacher}",
                fontFamily = MontserratFontFamily,
                fontStyle = FontStyle.Normal,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = USColor
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "${classs.startDate.format(timeFormatter)} - ${classs.endDate.format(timeFormatter)}",
                    fontFamily = MontserratFontFamily,
                    fontStyle = FontStyle.Normal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = USColor
                )

                Text(
                    text = classs.startDate.toLocalDate().formatDate(),
                    fontFamily = MontserratFontFamily,
                    fontStyle = FontStyle.Normal,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = USColor
                )
            }
        }
    }
}