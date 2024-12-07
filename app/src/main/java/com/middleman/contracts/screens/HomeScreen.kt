
package com.middleman.contracts.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.middleman.contracts.R
import com.middleman.contracts.components.BlinkingDot
import com.middleman.contracts.components.LiveOrders
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.ui.theme.ubuntuFontFamily
import com.middleman.contracts.utils.NotificationSharedPref
import com.middleman.contracts.utils.SharedPref
import com.middleman.contracts.viewmodel.CreatedOrdersViewModel
import com.middleman.contracts.viewmodel.NotificationViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, notificationViewModel: NotificationViewModel = viewModel()) {
    // State variable to control the dialog visibility
    var showDialog by remember { mutableStateOf(false) }
    var hasNewNotifications by remember { mutableStateOf(false) } // Track new notifications
    val context = LocalContext.current
    val userPhone = SharedPref.getPhone(LocalContext.current)

    // Handle back button press
    val activity = LocalContext.current as? Activity
    BackHandler {
        // Show the confirmation dialog when back is pressed
        showDialog = true
    }

    // Fetch notifications and determine if there are new ones
    LaunchedEffect(Unit) {
        notificationViewModel.fetchNotifications(userPhone) { notifications ->
            // Logic to determine if there are new notifications
            val lastSeenTimestamp = NotificationSharedPref.getLastSeenTimestamp(context)
            hasNewNotifications = notifications.any { it.timestamp > lastSeenTimestamp }
        }
    }

    // AlertDialog for confirmation
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Close App", fontFamily = ubuntuFontFamily, fontSize = 19.sp, fontWeight = FontWeight.SemiBold) },
            text = { Text("Do you want to close the app?", fontFamily = poppinsFontFamily, fontSize = 15.sp, fontWeight = FontWeight.Normal) },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                },
                    colors = ButtonDefaults.buttonColors(Color.Black)) {
                    Text("No", fontFamily = poppinsFontFamily)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    activity?.finishAffinity() // Close the app
                }) {
                    Text("Yes", fontFamily = poppinsFontFamily)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "middleman",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = ubuntuFontFamily
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ribbon),
                        contentDescription = "Ribbon Icon",
                        modifier = Modifier.size(35.dp)
                    )
                },
                actions = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.NotificationsNone,
                            contentDescription = "notification",
                            modifier = Modifier.clickable {
                                navController.navigate(Routes.Notifications.routes)
                            }
                        )
                        if (hasNewNotifications) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color.Red, shape = CircleShape)
                                    .align(Alignment.TopEnd)
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues).padding(top = 0.dp)) {

            // wallet
                Card(
                    modifier = Modifier.padding(16.dp)
                        .fillMaxWidth()
                        .height(160.dp),
                    colors = CardDefaults.cardColors(Color.LightGray),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column() {
                        Text(
                            text = "Wallet",
                            modifier = Modifier.padding(20.dp).padding(bottom = 15.dp),
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = poppinsFontFamily
                        )
                        Text(
                            text = "Total Orders: ",
                            modifier = Modifier.padding(start = 20.dp,bottom = 5.dp),
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily
                        )
                        Text(
                            text = "Order Amount: ",
                            modifier = Modifier.padding(start = 20.dp,bottom = 5.dp),
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFontFamily
                        )
                    }
                }


            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 2.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BlinkingDot()

                    Text(
                        text = "Live Orders",
                        modifier = Modifier
                            .padding(start = 8.dp, end = 5.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = ubuntuFontFamily
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(120.dp, 45.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.DarkGray)
                        .clickable {
                            navController.navigate(Routes.CreateOrder.routes) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = "Create Order",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = Color.White,
                        fontFamily = ubuntuFontFamily
                    )
                }
            }

            val orderViewModel: CreatedOrdersViewModel = viewModel()
            val userId = orderViewModel.getCurrentUserId()
            if (userId != null) {
                LiveOrders(
                    orderViewModel,
                    userId = userId,
                    navController = navController
                )
            }
        }
    }
}
