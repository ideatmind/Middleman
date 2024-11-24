package com.middleman.contracts.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.middleman.contracts.components.LiveOrders
import com.middleman.contracts.R
import com.middleman.contracts.components.WalletInfo
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.ui.theme.ubuntuFontFamily

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    // State variable to control the dialog visibility
    var showDialog by remember { mutableStateOf(false) }

    // Handle back button press
    val activity = LocalContext.current as? Activity
    BackHandler {
        // Show the confirmation dialog when back is pressed
        showDialog = true
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
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(end = 10.dp).clickable {
                                navController.navigate(Routes.Notifications.routes)
                            }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            WalletInfo()
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 2.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Live Orders",
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = ubuntuFontFamily
                )

                Box(
                    modifier = Modifier
                        .size(120.dp, 45.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF118114))
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
            LiveOrders()
        }
    }
}