package com.middleman.contracts.navigation

sealed class Routes (val routes : String){
    object Home : Routes("home")
    object Transactions : Routes("transactions")
    object Profile : Routes("profile")
    object Orders : Routes("orders")
    object CreateOrder : Routes("createOrder")
    object Notifications : Routes("notifications")
    object BottomNav : Routes("bottomNav")
    object Login : Routes("login")
    object Register : Routes("register")
    object ForgotPassword : Routes("forgotPassword")
    object OrderDetails : Routes("orderDetails")
}