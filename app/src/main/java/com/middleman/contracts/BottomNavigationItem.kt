package com.middleman.contracts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.screens.HomeScreen
import com.middleman.contracts.ui.theme.ubuntuFontFamily

data class BottomNavigation(
    val title: String,
    val selectedIcon : ImageVector,
    val unSelectedIcon :ImageVector
)

@Composable
fun BottomNav(navController: NavHostController) {
    val navController1 = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController1) }
    ) { innerPadding ->
        NavHost(navController = navController1, startDestination = Routes.Home.routes,
            modifier = Modifier.padding(innerPadding)) {
//            composable(Routes.Notification.routes) {
//                Notification()
//            }
            composable(Routes.Home.routes) {
                HomeScreen(navController)
            }
//            composable(Routes.Search.routes) {
//                Search()
//            }
//            composable(Routes.Profile.routes) {
//                Profile(navController)
//            }
//            composable(Routes.AddThreads.routes) {
//                AddThreads(navController1)
//            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val items = listOf(
        BottomNavigation(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home
        ),
        BottomNavigation(
            title = "Orders",
            selectedIcon = Icons.Filled.ShoppingCart,
            unSelectedIcon = Icons.Outlined.ShoppingCart
        ),
        BottomNavigation(
            title = "Transactions",
            selectedIcon = ImageVector.vectorResource(R.drawable.transactions_filled),
            unSelectedIcon = ImageVector.vectorResource(R.drawable.transactions_outlined)
        ),
        BottomNavigation(
            title = "Account",
            selectedIcon = Icons.Filled.AccountCircle,
            unSelectedIcon = Icons.Outlined.AccountCircle
        )
    )

    NavigationBar {
        Row(
            modifier = Modifier.background(Color.White)
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                item.selectedIcon
                            } else {
                                item.unSelectedIcon
                            },
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(26.dp) // Change the size here
                        )
                    },
                    label = {
                        Text(
                            item.title,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontFamily = ubuntuFontFamily
                        )
                    }

                )
            }
        }
    }
}
