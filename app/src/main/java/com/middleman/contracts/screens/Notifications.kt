package com.middleman.contracts.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.middleman.contracts.model.NotificationModel
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.utils.SharedPref
import com.middleman.contracts.viewmodel.NotificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Notifications(navController: NavHostController, notificationViewModel: NotificationViewModel = viewModel()) {
    val userPhone = SharedPref.getPhone(LocalContext.current) // Get the current user's email
    val notifications = remember { mutableStateListOf<NotificationModel>() }

    LaunchedEffect(userPhone) {
        notificationViewModel.fetchNotifications(userPhone) { fetchedNotifications ->
            notifications.clear()
            notifications.addAll(fetchedNotifications)
        }
    }
    Column {

        Scaffold(
            topBar = {
                TopAppBar(
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
                                    }
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
                                fontFamily = poppinsFontFamily
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
        ) {paddingValues ->

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                (notifications.reversed()).forEach { item ->
                    NotificationItem(
                        notificationName = item.notificationName,
                        notificationDescription = item.notificationDescription
                    )
                }

                Spacer(Modifier.height(25.dp))
            }
    }
    }
}

@Composable
fun NotificationItem(
    notificationName: String,
    notificationDescription: String
) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Rounded.NotificationsNone,
                contentDescription = "notification"
            )

            Column {
                Text(
                    text = notificationName,
                    fontFamily = poppinsFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
                )
                Text(
                    text = notificationDescription,
                    fontFamily = poppinsFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
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
