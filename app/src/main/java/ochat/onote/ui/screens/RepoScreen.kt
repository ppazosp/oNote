package ochat.onote.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ochat.onote.R
import ochat.onote.data.UIFiles
import ochat.onote.ui.theme.MontserratFontFamily
import ochat.onote.ui.theme.ONoteTheme
import ochat.onote.ui.theme.USColor
import ochat.onote.utils.downloadFile
import ochat.onote.utils.formatDate

@Preview
@Composable
fun RepoPreview(){
    ONoteTheme {
        RepoScreen(repoItems = listOf() )
    }
}

@Composable
fun RepoScreen(repoItems: List<UIFiles>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        RepoView(repoItems)
    }
}

@Composable
fun RepoView(repoItems: List<UIFiles>) {

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
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = USColor,
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(repoItems.size) { index ->
                RepoItem(repoItems[index])
            }
        }
    }
}

@Composable
fun RepoItem(file: UIFiles){
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            Icon(
                painter = painterResource(R.drawable.doc),
                contentDescription = "Document",
                tint = Color.Unspecified,
                modifier = Modifier.size(36.dp),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "${file.name}.${file.ext}",
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

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    text = file.owner,
                    fontFamily = MontserratFontFamily,
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = USColor,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,

                    modifier = Modifier
                        .weight(1f),
                    textAlign = TextAlign.Left,
                )

                Text(
                    text = file.date.formatDate(),
                    fontFamily = MontserratFontFamily,
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = USColor,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,

                    modifier = Modifier
                        .weight(0.4f),
                    textAlign = TextAlign.Right,
                )
            }
        }
    }
}