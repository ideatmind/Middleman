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
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.rounded.AddTask
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.MoneyOff
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.PermIdentity
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.middleman.contracts.R
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.utils.ProfilePref
import com.middleman.contracts.utils.SharedPref
import com.middleman.contracts.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController : NavHostController) {

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    LaunchedEffect(firebaseUser) {
        if(firebaseUser == null) {
            navController.navigate(Routes.Login.routes) {
                popUpTo(0)
                launchSingleTop = true
            }
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
                                modifier = Modifier.size(25.dp).clickable {
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
                                text = "Profile",
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
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = "Add Profile",
                                modifier = Modifier.size(25.dp)
                                    .clickable {
                                        navController.navigate(Routes.CreateProfile.routes)
                                    }
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

                    Spacer(Modifier.height(25.dp))

                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PermIdentity,
                            contentDescription = "name"
                        )
                        Text(text = ProfilePref.getName(context),
                            fontFamily = poppinsFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.DarkGray
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 35.dp, vertical = 10.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = "email"
                        )
                        Text(text = SharedPref.getUserEmail(context),
                            fontFamily = poppinsFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.DarkGray
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 35.dp, vertical = 10.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoneyOff,
                            contentDescription = "Bank"
                        )
                        Text(text = ProfilePref.getBankdetiles(context),
                            fontFamily = poppinsFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.DarkGray
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 35.dp, vertical = 10.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Password,
                            contentDescription = "password"
                        )
                        Text(
                            text = ProfilePref.getMobile(context),
                            fontFamily = poppinsFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.DarkGray
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 35.dp, vertical = 10.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.transactions_filled),
                            contentDescription = "transactions",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = ProfilePref.getShopAddress(context),
                            fontFamily = poppinsFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.DarkGray
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 35.dp, vertical = 10.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }

                Spacer(Modifier.height(100.dp))

                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp), contentAlignment = Alignment.Center)
                {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        onClick = {
                        authViewModel.logout()
                    }) {
                        Text("Logout", fontFamily = poppinsFontFamily)
                    }
                }
            }
        }
    }
}

