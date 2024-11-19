package com.middleman.contracts.navigation

sealed class Routes (val routes : String){
    object Home : Routes("home")
    object Search : Routes("search")
    object Profile : Routes("profile")
    object Splash : Routes("splash")
    object AddThreads : Routes("addThreads")
    object Notification : Routes("notifications")
    object BottomNav : Routes("bottomNav")
    object Login : Routes("login")
    object Register : Routes("register")

}