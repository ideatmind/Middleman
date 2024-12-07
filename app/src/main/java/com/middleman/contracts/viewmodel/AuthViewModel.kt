package com.middleman.contracts.viewmodel

import android.content.Context
import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.middleman.contracts.model.UserModel
import com.middleman.contracts.utils.SharedPref

class AuthViewModel: ViewModel() {

    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser : LiveData<FirebaseUser> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    init {
        _firebaseUser.value = auth.currentUser
    }

    fun login(email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    _firebaseUser.postValue(auth.currentUser)

                    getData(auth.currentUser!!.uid, context)
                }else {
                    _error.postValue(it.exception?.message ?: "Something went wrong")
                }
            }
    }


    private fun getData(uid: String, context: Context) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                if (userData != null) {
                    SharedPref.storeData(
                        email = userData.email,
                        userName = userData.userName,
                        password = userData.password,
                        otp = userData.otp,
                        context = context,
                        uid = uid,
                        phone = userData.phoneNo               )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _error.postValue("Failed to fetch user data: ${error.message}")
            }
        })
    }

    fun register(
        email: String,
        password: String,
        otp: String,
        phoneNo: String,
        userName: String,
        context: Context
    ) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    _firebaseUser.postValue(auth.currentUser)
                    auth.currentUser?.let { it1 -> saveData(
                        email = email,
                        password = password,
                        userName = userName,
                        otp = otp,
                        uid = it1.uid,
                        context = context,
                        phoneNo = phoneNo
                    ) }
                }else {
                    _error.postValue("Something went wrong")
                }
            }
    }


    private fun saveData(
        email: String,
        password: String,
        userName: String,
        phoneNo: String,
        otp: String,
        uid: String,
        context: Context
    ) {
        val userData = UserModel(email = email, password = password, userName = userName, otp = otp, uid = uid, phoneNo = phoneNo)

        userRef.child(uid).setValue(userData)
            .addOnSuccessListener {
                SharedPref.storeData(
                    email = email,
                    userName = userName,
                    password = password,
                    otp = otp,
                    context = context,
                    uid = uid,
                    phone = phoneNo
                )
            }.addOnFailureListener{

            }
    }

    fun logout() {
        auth.signOut()
        _firebaseUser.postValue(null)

    }
}

