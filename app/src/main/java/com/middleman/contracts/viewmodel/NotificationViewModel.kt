package com.middleman.contracts.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.middleman.contracts.model.NotificationModel
import com.middleman.contracts.model.OrderModel

class NotificationViewModel : ViewModel() {
    private val db = FirebaseDatabase.getInstance()
    private val notificationsRef = db.getReference("notifications")

    fun addNotification(phone: String, notification: NotificationModel) {
        val notificationId = notificationsRef.child(phone).push().key // Generate a unique key
        notificationId?.let {
            notificationsRef.child(phone).child(it).setValue(notification)
        }
    }



    fun fetchNotifications(phone: String, onResult: (List<NotificationModel>) -> Unit) {
        notificationsRef.child(phone).get().addOnSuccessListener { snapshot ->
            val notifications = mutableListOf<NotificationModel>()
            for (child in snapshot.children) {
                val notification = child.getValue(NotificationModel::class.java)
                notification?.let { notifications.add(it) }
            }
            onResult(notifications)
        }.addOnFailureListener {
            onResult(emptyList()) // Handle error
        }
    }


}

