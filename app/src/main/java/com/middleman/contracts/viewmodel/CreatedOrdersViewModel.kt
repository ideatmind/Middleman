package com.middleman.contracts.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.middleman.contracts.model.OrderModel
import com.middleman.contracts.model.UserModel

class CreatedOrdersViewModel: ViewModel() {

    val db = FirebaseDatabase.getInstance()
    val thread = db.getReference("threads")

    private val _ordersAndUsers = MutableLiveData<List<Pair<OrderModel, UserModel>>>()
    var ordersAndUsers : LiveData<List<Pair<OrderModel, UserModel>>> = _ordersAndUsers

    init {
        fetchOrdersAndUsers {
            _ordersAndUsers.value = it
        }
    }

    private fun fetchOrdersAndUsers(onResult: (List<Pair<OrderModel, UserModel>>) -> Unit) {
        thread.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<Pair<OrderModel, UserModel>>()
                for(orderSnapShot in snapshot.children) {
                    val order = orderSnapShot.getValue(OrderModel::class.java)
                    order.let {
                        fetchUserFromThread(it!!) {
                                user ->
                            result.add(0, it to user)

                            if(result.size == snapshot.childrenCount.toInt()) {
                                onResult(result)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchUserFromThread(order: OrderModel, onResult: (UserModel)->Unit) {
        db.getReference("users").child(order.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let(onResult)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }


    fun getOrderById(orderId: String): LiveData<OrderModel> {
        val orderLiveData = MutableLiveData<OrderModel>()
        db.getReference("orders").child(orderId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val order = snapshot.getValue(OrderModel::class.java)
                if (order != null) {
                    Log.d("CreatedOrdersViewModel", "Order fetched: $order")
                    orderLiveData.value = order
                } else {
                    Log.d("CreatedOrdersViewModel", "Order with ID $orderId does not exist.")
                    orderLiveData.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CreatedOrdersViewModel", "Error fetching order: ${error.message}")
                orderLiveData.value = null
            }

        })
        return orderLiveData
    }


}


