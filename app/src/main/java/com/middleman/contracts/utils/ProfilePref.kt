package com.middleman.contracts.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object ProfilePref {
    fun storeData(name: String,
                  email: String,
//                  mobile: String,
                  bankDetails : String,
                  shopAddress : String,
                  context: Context,
    ) {
        val profilePreferences = context.getSharedPreferences("profile", MODE_PRIVATE)
        val editor = profilePreferences.edit()
        editor.putString("name", name)
//        editor.putString("mobile", mobile
        editor.putString("email", email)
        editor.putString("bankDetails", bankDetails)
        editor.putString("shopAddress", shopAddress)
        editor.apply()
    }

    fun getUserName(context: Context) : String {
        val profilePreferences = context.getSharedPreferences("profile", MODE_PRIVATE)
        return profilePreferences.getString("name","")!!
    }

//    fun getMobile(context: Context) : String {
//        val profilePreferences = context.getSharedPreferences("profile", MODE_PRIVATE)
//        return profilePreferences.getString("mobile","")!!
//    }

    fun getEmail(context: Context) : String {
        val profilePreferences = context.getSharedPreferences("profile", MODE_PRIVATE)
        return profilePreferences.getString("email","")!!
    }

    fun getBankDetails(context: Context) : String {
        val profilePreferences = context.getSharedPreferences("profile", MODE_PRIVATE)
        return profilePreferences.getString("bankDetails","")!!
    }

    fun getShopAddress(context: Context) : String {
        val profilePreferences = context.getSharedPreferences("profile", MODE_PRIVATE)
        return profilePreferences.getString("shopAddress","")!!
    }
}