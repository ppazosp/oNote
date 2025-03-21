package ochat.onote.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ochat.onote.data.Task
import ochat.onote.data.UIReminder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import ochat.onote.ui.theme.MontserratFontFamily
import ochat.onote.ui.theme.ONoteTheme
import ochat.onote.ui.theme.USColor
import ochat.onote.utils.taskMap
import ochat.onote.utils.formatDate
import ochat.onote.utils.timeFormatter
import java.time.Month
import java.util.Locale


@Preview
@Composable
fun CalendarPreview(){
    ONoteTheme {
        CalendarScreen(reminderItems = listOf())
    }
}

@Composable
fun CalendarScreen(reminderItems: List<UIReminder>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        MonthView(reminderItems)
    }
}

fun groupRemindersByDate(reminders: List<UIReminder>): Map<LocalDate, List<UIReminder>> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Adjust format as needed

    return reminders.groupBy { reminder ->
        reminder.date.toLocalDate()
    }
}

@Composable
fun MonthView(reminderItems: List<UIReminder>) {

    val calendarMap = groupRemindersByDate(reminderItems)

    val today = LocalDate.now()
    val currentYear = today.year
    val columnCount = 7

    val startMonth = if (today.month.value >= Month.SEPTEMBER.value) {
        LocalDate.of(currentYear, Month.SEPTEMBER, 1)
    } else {
        LocalDate.of(currentYear - 1, Month.SEPTEMBER, 1)
    }

    val months = (0 until 10).map { startMonth.plusMonths(it.toLong()) }

    val allDays = mutableListOf<LocalDate?>()
    val monthLabels = mutableMapOf<Int, String>()

    months.forEach { month ->
        val monthDays = (1..month.lengthOfMonth()).map { month.withDayOfMonth(it) }
        val firstDayWeekday = (monthDays.first().dayOfWeek.value + 6) % 7

        val labelIndex = allDays.size
        monthLabels[labelIndex] = month.month
            .getDisplayName(java.time.format.TextStyle.FULL, Locale("es", "ES"))
            .uppercase(Locale("es", "ES")) + " " + month.year

        val lastColumn = allDays.size % columnCount
        if (lastColumn > 0) {
            repeat(columnCount - lastColumn) { allDays.add(null) }
        }

        repeat(firstDayWeekday) { allDays.add(null) }

        allDays.addAll(monthDays)

        val lastColumnAfterMonth = allDays.size % columnCount
        if (lastColumnAfterMonth > 0) {
            repeat(columnCount - lastColumnAfterMonth) { allDays.add(null) }
        }
    }

    var expandedDay by remember { mutableStateOf<LocalDate?>(null) }
    var clickedPosition by remember { mutableStateOf<Offset?>(null) }
    var monthCardSize by remember { mutableStateOf<IntSize?>(null) }
    var monthCardOffset by remember { mutableStateOf<Offset?>(null) }

    val dayPositions = remember { mutableStateMapOf<LocalDate, Offset>() }
    val listState = rememberLazyListState()

    val startOfCurrentMonthIndex = monthLabels.keys.firstOrNull { key ->
        allDays.getOrNull(key)?.month == today.month && allDays.getOrNull(key)?.year == today.year
    } ?: allDays.indexOfFirst { it?.month == today.month && it?.year == today.year }

    LaunchedEffect(startOfCurrentMonthIndex) {
        val correctedRowIndex = startOfCurrentMonthIndex / columnCount
        listState.scrollToItem(correctedRowIndex)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned { coordinates ->
            monthCardOffset = coordinates.positionInRoot()
            monthCardSize = coordinates.size
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "CALENDARIO",
                fontFamily = MontserratFontFamily,
                fontStyle = FontStyle.Normal,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = USColor
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),

                ) {
                val weeks = allDays.chunked(columnCount)

                // MONTH LABELS
                itemsIndexed(weeks) { index, week ->
                    monthLabels[index * columnCount]?.let {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(start = 8.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = it,
                                fontFamily = MontserratFontFamily,
                                fontStyle = FontStyle.Normal,
                                fontSize = 20.sp,
                                color = USColor
                            )
                        }
                    }

                    // WEEKS
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        week.forEach { day ->
                            if (day == null) {
                                Spacer(modifier = Modifier.weight(1f).aspectRatio(1f))
                            } else {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .alpha(if (expandedDay == day) 0f else 1f)
                                        .onGloballyPositioned { coordinates ->
                                            monthCardOffset?.let { boxOffset ->
                                                val absolutePosition = coordinates.boundsInRoot().topLeft
                                                val relativePosition = absolutePosition - boxOffset
                                                dayPositions[day] = relativePosition
                                            }
                                        }
                                        .clickable {
                                            clickedPosition = dayPositions[day]
                                            expandedDay = day
                                        }
                                        .border(2.dp, USColor)
                                        .background(if(day == today) USColor else Color.White)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${day.dayOfMonth}",
                                            color = if(day == today) Color.White else USColor,
                                            fontFamily = MontserratFontFamily,
                                            fontStyle = FontStyle.Normal,
                                            fontSize = 18.sp,
                                        )
                                    }

                                    if (calendarMap.containsKey(day)) {
                                        Box(
                                            modifier = Modifier
                                                .size(12.dp)
                                                .background(if(day == today ) Color.White else USColor),
                                        ){}
                                    }
                                }
                            }
                        }
                    }

                    val isLastRowOfMonth = week.any { it != null && it.dayOfMonth == it.month.length(it.isLeapYear) }

                    if (isLastRowOfMonth) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(columnCount) {
                                Spacer(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(2f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (expandedDay != null && clickedPosition != null && monthCardSize != null) {

        DayView(
            day = expandedDay!!,
            clickedPosition = clickedPosition!!,
            monthSize = monthCardSize!!,
            events = calendarMap[expandedDay!!] ?: emptyList(),
            onBack = {
                expandedDay = null
                clickedPosition = null
            }
        )
    }
}


@Composable
fun DayView(
    day: LocalDate,
    clickedPosition: Offset,
    monthSize: IntSize,
    events: List<UIReminder>,
    onBack: () -> Unit
) {
    var moveToCenterStarted by remember { mutableStateOf(false) }
    var sizeAnimationStarted by remember { mutableStateOf(false) }
    var isClosing by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    var showContent by remember { mutableStateOf(false) }

    // OFFSET CALCULATIONS
    val density = LocalDensity.current
    val columnSpacing = 8.dp
    val columnSpacingPx = with(density) { columnSpacing.toPx() }
    val dayWidthPx = (monthSize.width - (columnSpacingPx * (7 - 1))) / 7f
    val dayWidth = with(density) { dayWidthPx.toDp() }

    val initialXOffsetDp = with(density) { clickedPosition.x.toDp() }
    val initialYOffsetDp = with(density) { clickedPosition.y.toDp() }

    val monthSizeX = with(density) { (monthSize.width).toDp() }
    val monthSizeY = with(density) { (monthSize.height).toDp() }

    val centerX = with(density) { (monthSize.width / 2).toDp() - (dayWidth / 2) }
    val centerY = with(density) { (monthSize.height / 2).toDp() - (dayWidth / 2) }

    // ANIMATIONS
    val offsetX by animateDpAsState(
        targetValue = if (moveToCenterStarted) centerX else initialXOffsetDp,
        animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing),
        label = "OffsetX"
    )

    val offsetY by animateDpAsState(
        targetValue = if (moveToCenterStarted) centerY else initialYOffsetDp,
        animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing),
        label = "OffsetY"
    )

    val width by animateDpAsState(
        targetValue = if (sizeAnimationStarted) monthSizeX else dayWidth,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        finishedListener = { if (!isClosing) showContent = true },
        label = "WidthAnimation"
    )

    val height by animateDpAsState(
        targetValue = if (sizeAnimationStarted) monthSizeY else dayWidth,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "HeightAnimation"
    )

    val adjustedOffsetX by animateDpAsState(
        targetValue = if (sizeAnimationStarted) 0.dp else offsetX,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "AdjustedOffsetX"
    )

    val adjustedOffsetY by animateDpAsState(
        targetValue = if (sizeAnimationStarted) 0.dp else offsetY,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "AdjustedOffsetY"
    )

    LaunchedEffect(Unit) {
        Log.d("ExpandingDayView", "Moving to center for day $day from position: $clickedPosition")
        moveToCenterStarted = true
        delay(300)

        Log.d("ExpandingDayView", "Expanding day view for $day")
        sizeAnimationStarted = true
        delay(300)

        isExpanded = true
    }

    LaunchedEffect(isClosing) {
        if (isClosing) {
            showContent = false
            isExpanded = false
            sizeAnimationStarted = false
            delay(300)
            moveToCenterStarted = false
            delay(300)
            onBack()
        }
    }

    // COMPOSE
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Box(
            modifier = Modifier
                .offset(x = adjustedOffsetX, y = adjustedOffsetY)
                .size(
                    if (!isExpanded) width else monthSizeX ,
                    if (!isExpanded) height else monthSizeY - 32.dp
                )
                .graphicsLayer(scaleX = 1f, scaleY = 1f, shape = RoundedCornerShape(16.dp))
                .clickable {
                    isClosing = true
                }
                .background(Color.White)
                .border(2.dp, USColor),
        ) {

            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(animationSpec = tween(200)) +
                        slideInVertically(initialOffsetY = { it / 4 }, animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(200)) +
                        slideOutVertically(targetOffsetY = { it / 4 }, animationSpec = tween(200))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Text(
                        text = day.formatDate().uppercase(),
                        fontFamily = MontserratFontFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, bottom = 8.dp),
                        color = USColor
                    )

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(events) { event ->
                            EventCard(
                                event
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(event: UIReminder) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White)
            .border(2.dp, USColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(USColor),
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ) {
                Text(
                    text = event.name.uppercase(),
                    fontFamily = MontserratFontFamily,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = USColor
                )

                Text(
                    text = event.description.uppercase(),
                    fontFamily = MontserratFontFamily,
                    fontStyle = FontStyle.Normal,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    color = USColor
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = event.date.format(timeFormatter),
                    fontFamily = MontserratFontFamily,
                    fontStyle = FontStyle.Normal,
                    fontSize = 16.sp,
                    color = USColor
                )
            }
        }
    }
}


