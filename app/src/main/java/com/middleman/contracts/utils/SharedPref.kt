package com.middleman.contracts.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.firebase.database.FirebaseDatabase

object SharedPref {
    fun storeData(email: String,
//                  name: String,
                  userName: String,
                  password: String,
                  otp : String,
//                  imageUri: String,
                  context: Context,
                  uid: String
    ) {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("username", userName)
        editor.putString("otp", otp)
        editor.apply()
    }

    fun getUserName(context: Context) : String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("username","")!!
    }
    fun getUserEmail(context: Context) : String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("email","")!!
    }
    fun getUserOtp(context: Context) : String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("otp","")!!
    }
    fun updateUserName(userId: String, newName: String,context: Context) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId)
        databaseReference.child("userName").setValue(newName)
    }

}