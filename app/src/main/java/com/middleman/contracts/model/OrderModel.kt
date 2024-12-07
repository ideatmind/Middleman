package com.middleman.contracts.model

data class OrderModel(
    val seller: String = "",
    val sellerPhone: String = "",
    val sellerEmail: String = "",
    val customer: String = "",
    val customerEmail: String = "",
    val productName: String = "",
    val orderKey: String = "",
    val productCost: String = "",
    val productQuantity: String = "",
    val totalAmount: String = "",
    val userId: String = "",
    val timeStamp : String = ""
)