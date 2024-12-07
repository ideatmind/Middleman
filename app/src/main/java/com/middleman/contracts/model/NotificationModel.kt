package com.middleman.contracts.model

data class NotificationModel(
    val notificationName: String = "",
    val notificationDescription: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val sellerEmail: String = "",
    val customerEmail: String = "",
    val type: String = "General",
    val orderId: String? = null,
    val isRead: Boolean = false,
    val timeSeen: Long = 0L
)
