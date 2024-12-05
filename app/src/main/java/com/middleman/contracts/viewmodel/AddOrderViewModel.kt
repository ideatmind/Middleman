package com.middleman.contracts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.middleman.contracts.model.OrderModel

class AddOrderViewModel: ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("orders")

    private val _orderKey = MutableLiveData<String>()
    val orderKey: LiveData<String> = _orderKey

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted : LiveData<Boolean> = _isPosted

    //    fun saveImage(
//        thread: String,
//        userId: String,
//        imageUri: Uri
//    ) {
//        val uploadTask = imageRef.putFile(imageUri)
//        uploadTask.addOnSuccessListener {
//            imageRef.downloadUrl.addOnSuccessListener {
//                saveData(thread, userId, it.toString())
//            }
//        }
//    }

    fun saveData(
        seller: String,
        sellerPhone: String,
        customer: String,
        customerPhone: String,
        productName: String,
        productCost: String,
        productQuantity: String,
        totalAmount: String,
        userId: String,
    ) {
        val orderData = OrderModel(
            seller,
            sellerPhone,
            customer,
            customerPhone,
            productName,
            productCost,
            productQuantity,
            totalAmount,
            userId,
            System.currentTimeMillis().toString()
        )

        val orderKey = userRef.push().key ?: return // Get the key for the new order
        userRef.child(orderKey).setValue(orderData)
            .addOnSuccessListener {
                _isPosted.postValue(true)
                _orderKey.postValue(orderKey) // Post the order key
            }.addOnFailureListener {
                _isPosted.postValue(false)
            }


    }


}

