//package com.middleman.contracts.viewmodel
//
//import android.content.Context
//import androidx.compose.ui.platform.LocalContext
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.google.firebase.database.FirebaseDatabase
//import com.middleman.contracts.model.ProfileModel
//import com.middleman.contracts.utils.ProfilePref
//
//class ProfileModelViewModel:ViewModel() {
//
//    private val db = FirebaseDatabase.getInstance()
//    val profileRef = db.getReference("profile")
//
//    private val _isAdded = MutableLiveData<Boolean>()
//    val isAdded : LiveData<Boolean> = _isAdded
//
//
//    fun addProfile(name:String,mobile:String,bankDetails:String,shopAddress:String,userId : String, context: Context){
//        val profileData = ProfileModel(name,mobile,bankDetails,shopAddress,userId)
//        profileRef.child(userId).setValue(profileData)
//            .addOnSuccessListener {
//                _isAdded.postValue(true)
//                ProfilePref.storeData(
//                    name = name,
//                    mobile = mobile,
//                    bankdetailes = bankDetails,
//                    shopAddress = shopAddress,
//                    context = context,
//                    email = TODO(),
//                    bankDetails = TODO(),
//                )
//            }.addOnFailureListener {
//                _isAdded.postValue(false)
//            }
//    }
//    fun updateProfile(
//        userId: String,
//        name: String,
//        mobile: String,
//        bankDetails: String,
//        shopAddress: String,
//        updatedData: Map<String, Any>,
//        context: Context
//    ) {
//        val profileData = ProfileModel(name,mobile,bankDetails,shopAddress,userId)
//        profileRef.child(userId).updateChildren(updatedData)
//            .addOnSuccessListener {
//                _isAdded.postValue(true)
//                ProfilePref.storeData(
//                    name = name,
//                    mobile = mobile,
//                    bankdetailes = bankDetails,
//                    shopAddress = shopAddress,
//                    context = context,
//                )
//            }.addOnFailureListener {
//                _isAdded.postValue(false)
//            }
//    }
//}