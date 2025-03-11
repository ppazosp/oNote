package ochat.onote.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import ochat.onote.R

enum class Screen(val title: String, val icon: Int) {
    Calendar("Calendar",  R.drawable.calendar),
    Class("Class", R.drawable.audio),
    Repo("Repo", R.drawable.attach)
}