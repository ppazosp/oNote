package ochat.onote.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import ochat.onote.ui.screens.ClassScreen
import ochat.onote.ui.screens.RepoScreen
import ochat.onote.ui.screens.Screen
import ochat.wearendar.ui.screens.CalendarScreen

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph() {
    val screens = listOf(Screen.Calendar, Screen.Class, Screen.Repo)
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { screens.size })
    val coroutineScope = rememberCoroutineScope()

    var currentPage by remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }

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
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) {
                page ->
            when (screens[page]) {
                Screen.Calendar -> CalendarScreen()
                Screen.Class -> ClassScreen()
                Screen.Repo -> RepoScreen()
            }
        }
    }
}