package com.middleman.contracts.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPref {
    fun storeData(email: String,
//                  name: String,
                  userName: String,
                  password: String,
                  otp : String,
//                  imageUri: String,
                  phone: String,
                  context: Context,
                  uid: String
    ) {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("phone", phone)
        editor.putString("password", password)
        editor.putString("username", userName)
        editor.putString("otp", otp)
        editor.apply()
    }

    fun getUserName(context: Context) : String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("username","")!!
    }

    fun getEmail(context: Context) : String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("email","")!!
    }

    fun getPhone(context: Context) : String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("phone","")!!
    }


}

