package com.middleman.contracts.model

data class OrderModel(
    val seller: String = "",
    val customer: String = "",
    val productName: String = "",
    val productCost: String = "",
    val productQuantity: String = "",
    val totalAmount: String = "",
    val userId: String = "",
    val timeStamp : String = ""
)