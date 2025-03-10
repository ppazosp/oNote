package ochat.onote.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import ochat.onote.ui.screens.Screen
import ochat.onote.ui.theme.USColor

@Composable
fun BottomNavigationBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    val screens = listOf(Screen.Calendar, Screen.Grid, Screen.Repo)

    AnimatedNavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        selectedIndex = selectedIndex,
        barColor = USColor,
        ballColor = USColor,
        ballAnimation = Parabolic(tween(300))
    ) {
        screens.forEachIndexed { index, screen ->
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clickable { onItemSelected(index) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = screen.title,
                    modifier = Modifier.size(24.dp),
                    tint = if (selectedIndex != index) Color.LightGray else Color.White
                )
            }
        }
    }
}