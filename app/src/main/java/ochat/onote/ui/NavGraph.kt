package ochat.onote.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ochat.onote.data.UIClass
import ochat.onote.data.UIFiles
import ochat.onote.data.UIReminder
import ochat.onote.data.getClasses
import ochat.onote.data.getFiles
import ochat.onote.data.getReminders
import ochat.onote.ui.screens.CalendarScreen
import ochat.onote.ui.screens.ClassScreen
import ochat.onote.ui.screens.LoadingScreen
import ochat.onote.ui.screens.RepoScreen
import ochat.onote.ui.screens.Screen
import ochat.onote.ui.theme.MontserratFontFamily
import ochat.onote.ui.theme.ONoteTheme
import ochat.onote.ui.theme.USColor

@Preview
@Composable
fun NavGraphView(){
    ONoteTheme {
        NavGraph("TEST", { _, _ -> } )
    }
}

@Composable
fun NavGraph(subjectName: String, onOpenStreaming: (isOnline: Boolean, String) -> Unit) {
    val screens = listOf(Screen.Calendar, Screen.Class, Screen.Repo)
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { screens.size })
    val coroutineScope = rememberCoroutineScope()

    //FETCH

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(USColor)
    }

    var currentPage by remember { mutableIntStateOf(0) }
    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }

    var repoItems by remember { mutableStateOf<List<UIFiles>>(emptyList()) }
    var classItems by remember { mutableStateOf<List<UIClass>>(emptyList()) }
    var reminderItems by remember { mutableStateOf<List<UIReminder>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true

        val filesDeferred = async(Dispatchers.IO) { getFiles(subjectName) }
        val classesDeferred = async(Dispatchers.IO) { getClasses(subjectName) }
        val remindersDeferred = async(Dispatchers.IO) { getReminders(subjectName) }

        repoItems = filesDeferred.await()
        classItems = classesDeferred.await()
        reminderItems = remindersDeferred.await()

        isLoading = false
    }

    if (isLoading) {
        LoadingScreen()
    }else {

        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    selectedIndex = currentPage,
                    onItemSelected = { page ->
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = subjectName,
                        fontFamily = MontserratFontFamily,
                        fontStyle = FontStyle.Normal,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = USColor
                    )
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                ) { page ->
                    when (screens[page]) {
                        Screen.Calendar -> CalendarScreen(reminderItems)
                        Screen.Class -> ClassScreen(classItems, onOpenStreaming)
                        Screen.Repo -> RepoScreen(repoItems)
                    }
                }
            }
        }
    }
}