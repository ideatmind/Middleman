package com.middleman.contracts.model

data class UserModel(
    val email: String = "",
    val password: String = "",
//    val name: String = "",
    val userName: String = "",
    val otp : String = "",
//    val imageUri: String = "",
    val uid: String = ""
)