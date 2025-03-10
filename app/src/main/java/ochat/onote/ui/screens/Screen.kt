package ochat.onote.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(val title: String, val icon: ImageVector) {
    Calendar("Calendar",  Icons.Filled.DateRange),
    Grid("Grid", Icons.Filled.AddCircle),
    Repo("Repo", Icons.Filled.Menu)
}