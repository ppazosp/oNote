package ochat.onote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ochat.onote.ui.NavGraph
import ochat.onote.ui.screens.GridScreen
import ochat.onote.ui.screens.StreamingScreen
import ochat.onote.ui.theme.ONoteTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ONoteTheme {
                App()
            }
        }
    }




}

@Composable
fun App() {
    var showNavGraph by remember { mutableStateOf(false) }
    var showStreamingScreen by remember { mutableStateOf(false) }
    var subjectName by remember { mutableStateOf<String?>(null) }

    AnimatedContent(
        targetState = Triple(showNavGraph, showStreamingScreen, subjectName),
        transitionSpec = {
            fadeIn(animationSpec = tween(700)) togetherWith fadeOut(animationSpec = tween(700))
        }
    ) { (isNavGraphVisible, isStreamingVisible, selectedSubject) ->
        when {
            isStreamingVisible -> {
                BackHandler { showStreamingScreen = false }
                StreamingScreen()
            }

            isNavGraphVisible -> {
                BackHandler { showNavGraph = false }
                NavGraph(
                    subjectName = subjectName!!,
                    onOpenStreaming = { showStreamingScreen = true },
                )
            }

            else -> {
                GridScreen { name ->
                    showNavGraph = true
                    subjectName = name
                }
            }
        }
    }
}