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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

enum class NotificationTab { ALL, UNREAD, READ }
data class Notification(val title: String, val message: String, val isRead: Boolean, val timestamp: LocalDateTime)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(NotificationTab.ALL) }
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
        )
    )
    val filteredNotifications = when (selectedTab) {
        NotificationTab.ALL -> notifications
        NotificationTab.UNREAD -> notifications.filter { !it.isRead }
        NotificationTab.READ -> notifications.filter { it.isRead }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(R.string.notifications)) },
        )
        TabRow(selectedTabIndex = selectedTab.ordinal) {
            Tab(
                selected = selectedTab == NotificationTab.ALL,
                onClick = { selectedTab = NotificationTab.ALL },
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selectedTab == NotificationTab.ALL) Color.Red else Color.Transparent)
                    .padding(8.dp)
            ) {
                Text(
                    stringResource(R.string.all),
                    color = if (selectedTab == NotificationTab.ALL) Color.White else Color.Black
                )
            }
            Tab(
                selected = selectedTab == NotificationTab.UNREAD,
                onClick = { selectedTab = NotificationTab.UNREAD },
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selectedTab == NotificationTab.UNREAD) Color.Red else Color.Transparent)
                    .padding(8.dp)
            ) {
                Text(
                    stringResource(R.string.unread),
                    color = if (selectedTab == NotificationTab.UNREAD) Color.White else Color.Black
                )
            }
            Tab(
                selected = selectedTab == NotificationTab.READ,
                onClick = { selectedTab = NotificationTab.READ },
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selectedTab == NotificationTab.READ) Color.Red else Color.Transparent)
                    .padding(8.dp)
            ) {
                Text(
                    stringResource(R.string.read),
                    color = if (selectedTab == NotificationTab.READ) Color.White else Color.Black
                )
            }
        }
        LazyColumn {
            item {
                SectionHeader(stringResource(R.string.today))
            }
            items(filteredNotifications.filter { it.timestamp.isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) }) { notification ->
                NotificationCard(notification = notification)
            }
            item {
                SectionHeader(stringResource(R.string.yesterday))
            }
            items(filteredNotifications.filter { it.timestamp.isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(1)) && it.timestamp.isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) }) { notification ->
                NotificationCard(notification = notification)
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
            .clip(RoundedCornerShape(16.dp))
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
                Text(text = notification.title, fontSize = 20.sp, color = Color.Black)
                Text(text = notification.message, fontSize = 16.sp, color = Color.Gray)
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
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
