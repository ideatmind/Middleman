package com.middleman.contracts.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.middleman.contracts.BottomNavigationBar
import com.middleman.contracts.components.LiveOrders
import com.middleman.contracts.R
import com.middleman.contracts.components.WalletInfo
import com.middleman.contracts.ui.theme.ubuntuFontFamily

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
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
                }
            ,
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
                            .padding(end = 10.dp) // Adjust size as needed
                    )
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        },
    ) {paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            WalletInfo()
//            Spacer(Modifier.height(5.dp))
            Row (
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 2.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
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
                        .background(Color( 0xFF118114))
                        .clickable { },
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








