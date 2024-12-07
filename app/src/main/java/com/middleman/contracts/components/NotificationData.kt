package com.middleman.contracts.components

data class NotificationData(
    val notificationName: String,
    val notificationDescription: String,
    val timestamp: Long = System.currentTimeMillis(), // Default to current time
    val type: String = "General", // Default type
    val orderId: String? = null, // Optional order ID
    val isRead: Boolean = false // Default to unread
)
