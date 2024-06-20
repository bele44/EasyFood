package com.example.easyfood.ui.food

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easyfood.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.launch

enum class NotificationTab { ALL, UNREAD, READ }
data class Notification(val title: String, val message: String, val isRead: Boolean, val timestamp: LocalDateTime)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun NotificationsScreen(navController: NavController) {
    val pagerState = rememberPagerState()
    val notifications = listOf(
        Notification(
            stringResource(R.string.new_message_title),
            stringResource(R.string.new_message_description),
            false,
            LocalDateTime.now().minusHours(1)
        ),
        Notification(
            stringResource(R.string.order_delivered_title),
            stringResource(R.string.order_delivered_description),
            true,
            LocalDateTime.now().minusDays(1)
        ),
        Notification(
            stringResource(R.string.promotion_title),
            stringResource(R.string.promotion_description),
            false,
            LocalDateTime.now().minusDays(1).minusHours(2)
        ),
                Notification(
                title = "New Friend Request",
        message = "You have a new food request from John Doe.",
        isRead = false,
        timestamp = LocalDateTime.now().minusHours(2)
    ),

    Notification(
        title = "Weekly Summary",
        message = "Your weekly food is ready.",
        isRead = true,
        timestamp = LocalDateTime.now().minusDays(1).minusHours(3)
    )
    )

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)) {
        TopAppBar(
            title = { Text(stringResource(R.string.notifications), modifier = Modifier.background(MaterialTheme.colorScheme.primary)

            ) },
        )
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .height(4.dp)
                        .background(Color.Red)
                )
            }
        ) {
            NotificationTab.values().forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (pagerState.currentPage == index) Color.Transparent else Color.Transparent)
                        .padding(8.dp)
                ) {
                    Text(
                        stringResource(
                            when (tab) {
                                NotificationTab.ALL -> R.string.all
                                NotificationTab.UNREAD -> R.string.unread
                                NotificationTab.READ -> R.string.read
                            }
                        ),
                        color = if (pagerState.currentPage == index) Color.Red else Color.Black
                    )
                }
            }
        }
        HorizontalPager(
            count = NotificationTab.values().size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val filteredNotifications = when (NotificationTab.values()[page]) {
                NotificationTab.ALL -> notifications
                NotificationTab.UNREAD -> notifications.filter { !it.isRead }
                NotificationTab.READ -> notifications.filter { it.isRead }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                item {
                    if (filteredNotifications.any { it.timestamp.isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) }) {
                        SectionHeader(stringResource(R.string.today))
                    }
                }
                items(filteredNotifications.filter { it.timestamp.isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) }) { notification ->
                    NotificationCard(notification = notification)
                }
                item {
                    SectionHeader(stringResource(R.string.yesterday))
                }
                items(filteredNotifications.filter {
                    it.timestamp.isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(1)) &&
                            it.timestamp.isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))
                }) { notification ->
                    NotificationCard(notification = notification)
                }
               /* item {
                    SectionHeader(stringResource(R.string.earlier))
                }
                items(filteredNotifications.filter {
                    it.timestamp.isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(2))
                }) { notification ->
                    NotificationCard(notification = notification)
                }*/
            }
        }
    }
}

@Composable
fun NotificationCard(notification: Notification) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.avatar2__2_),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = notification.title, fontSize = 16.sp, color = Color.Black)
                Text(text = notification.message, fontSize = 14.sp, color = Color.Gray)
            }
            if (!notification.isRead) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Red)
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
