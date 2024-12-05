package com.middleman.contracts.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object ProfilePref {
    fun storeData(name: String,
                  mobile: String,
                  bankdetailes : String,
                  shopAddress : String,
                  context: Context,
    ) {
        val profilePreferences = context.getSharedPreferences("profile", MODE_PRIVATE)
        val editor = profilePreferences.edit()
        editor.putString("name", name)
        editor.putString("mobile", mobile)
        editor.putString("bankdetailes", bankdetailes)
        editor.putString("shopAddress", shopAddress)
        editor.apply()
    }

    fun getName(context: Context) : String {
        val profilePreferences = context.getSharedPreferences("profile", MODE_PRIVATE)
        return profilePreferences.getString("name","")!!
    }

    fun getMobile(context: Context) : String {
        val profilePreferences = context.getSharedPreferences("profile", MODE_PRIVATE)
        return profilePreferences.getString("mobile","")!!
    }

    fun getBankdetiles(context: Context) : String {
        val profilePreferences = context.getSharedPreferences("profile", MODE_PRIVATE)
        return profilePreferences.getString("bankdetailes","")!!
    }

    fun getShopAddress(context: Context) : String {
        val profilePreferences = context.getSharedPreferences("profile", MODE_PRIVATE)
        return profilePreferences.getString("shopAddress","")!!
    }
}