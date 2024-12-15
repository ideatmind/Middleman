package com.middleman.contracts.screens

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddTask
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.middleman.contracts.model.NotificationModel
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.CardColor
import com.middleman.contracts.ui.theme.TitleBackColor
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.ui.theme.ubuntuFontFamily
import com.middleman.contracts.utils.NotificationSharedPref
import com.middleman.contracts.utils.SharedPref
import com.middleman.contracts.viewmodel.NotificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Notifications(
    navController: NavHostController,
    notificationViewModel: NotificationViewModel = viewModel()
) {
    val userPhone = SharedPref.getPhone(LocalContext.current)
    val notifications = remember { mutableStateListOf<NotificationModel>() }
    val lastSeenTimestamp = NotificationSharedPref.getLastSeenTimestamp(LocalContext.current)
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(userPhone) {
        notificationViewModel.fetchNotifications(userPhone) { fetchedNotifications ->
            notifications.clear()
            notifications.addAll(fetchedNotifications.map {
                it.copy(hasNewNotification = it.timestamp > lastSeenTimestamp) // Set based on timestamp
            })
            isLoading.value = false
            NotificationSharedPref.setLastSeenTimestamp(context, System.currentTimeMillis())
        }
    }

    Column() {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarColors(
                        containerColor = TitleBackColor,
                        scrolledContainerColor = TitleBackColor,
                        navigationIconContentColor = TitleBackColor,
                        titleContentColor = TitleBackColor,
                        actionIconContentColor = TitleBackColor
                    ),
                    navigationIcon = {
                        Box(
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBackIosNew,
                                contentDescription = "back navigation",
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {
                                        navController.navigate(Routes.BottomNav.routes)
                                    },
                                tint = Color.Black
                            )
                        }
                    },
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Notifications",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Normal,
                                fontFamily = poppinsFontFamily,
                                color = Color.Black
                            )
                        }
                    },
                    actions = {
                        Box(
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AddTask,
                                contentDescription = "Create Order",
                                modifier = Modifier.size(25.dp),
                                tint = Color.Transparent
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->

            if (isLoading.value) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Text(
                        text = "Loading notifications...",
                        color = Color.Black,
                        fontFamily = poppinsFontFamily,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .wrapContentSize(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    item {
                        notifications.reversed().take(15).forEach { item ->
                            NotificationItem(
                                notificationName = item.notificationName,
                                notificationDescription = item.notificationDescription,
                                hasNewNotification = item.hasNewNotification,
                                timeStamp = item.timestamp.toString(),
                                onNotificationClicked = {
                                    item.hasNewNotification = false
                                },
                                navController = navController
                            )
                        }

                        Spacer(Modifier.height(25.dp))

                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(
    notificationName: String,
    notificationDescription: String,
    timeStamp: String,
    hasNewNotification: Boolean,
    onNotificationClicked: () -> Unit,
    navController: NavHostController
) {
    var isNotificationClicked by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .clickable {
                isNotificationClicked = true
                onNotificationClicked()
                navController.navigate(Routes.Orders.routes)
            }.background(Color.White), horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Box {
                Icon(
                    imageVector = Icons.Rounded.NotificationsNone,
                    contentDescription = "notification"
                )
                if (hasNewNotification && !isNotificationClicked) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color.Red, shape = CircleShape)
                            .align(Alignment.TopEnd)
                    )
                }
            }

            Column {
                val timeAndDate = try {
                    formatTimestamp(timeStamp.toLong())
                } catch (e: NumberFormatException) {
                    "Invalid date"
                }

                Text(
                    text = notificationName,
                    fontFamily = poppinsFontFamily,
                    fontSize = 20.sp,
                    fontWeight = if (hasNewNotification && !isNotificationClicked) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Black
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = if (hasNewNotification && !isNotificationClicked) FontWeight.Bold else FontWeight.Normal,
                                fontFamily = ubuntuFontFamily,
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                        ) {
                            append("( Date and Time: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = if (hasNewNotification && !isNotificationClicked) FontWeight.Bold else FontWeight.Normal,
                                fontFamily = ubuntuFontFamily,
                                fontSize = 12.sp,
                                color = Color.DarkGray
                            )
                        ) {
                            append("$timeAndDate )")
                        }
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = notificationDescription,
                    fontFamily = poppinsFontFamily,
                    fontSize = 16.sp,
                    fontWeight = if (hasNewNotification && !isNotificationClicked) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Gray
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 35.dp, vertical = 10.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
    }
}
