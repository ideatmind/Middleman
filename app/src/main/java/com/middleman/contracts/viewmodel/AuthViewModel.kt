package com.middleman.contracts.viewmodel

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.middleman.contracts.model.UserModel
import com.middleman.contracts.utils.SharedPref
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
                SharedPref.storeData(
                    email = userData!!.email,
                    userName = userData.userName,
                    password = userData.password,
                    otp = userData.otp,
                    context = context,
                    uid = uid
                )
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun register(
        email: String,
        password: String,
        otp: String,
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
                        context = context
                    ) }
                }else {
                    _error.postValue("Something went wrong")
                }
            }
    }

//    private fun saveImage(email: String, password: String, otp : String, userName: String, uid: String?, context: Context) {
//        val uploadTask = imageRef.putFile()
//        uploadTask.addOnSuccessListener {
//            imageRef.downloadUrl.addOnSuccessListener {
//                if (uid != null) {
//                    saveData(email, password, userName, uid, context)
//                }
//            }
//        }
//    }

    private fun saveData(
        email: String,
        password: String,
        userName: String,
        otp: String,
        uid: String,
        context: Context
    ) {
        val userData = UserModel(email = email, password = password, userName = userName, otp = otp, uid = uid)

        userRef.child(uid).setValue(userData)
            .addOnSuccessListener {
                SharedPref.storeData(
                    email = email,
                    userName = userName,
                    password = password,
                    otp = otp,
                    context = context,
                    uid = uid
                )
            }.addOnFailureListener{

            }
    }


//    fun sendOtp(email: String, context: Context) {
//        val actionCodeSettings = ActionCodeSettings.newBuilder()
//            .setUrl("https://threads-591ce.firebaseapp.com/__/auth/action?mode=action&oobCode=code") // Your URL
//            .setHandleCodeInApp(true)
//            .setAndroidPackageName(
//                "com.middleman.contracts",
//                true, /* installIfNotAvailable */
//                "12" /* minimumVersion */)
//            .build()
//
//        auth.sendSignInLinkToEmail(email, actionCodeSettings)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(context, "OTP sent to email", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "Error sending OTP: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

//    fun verifyOtp(email: String, otp: String, context: Context) {
//        if (auth.isSignInWithEmailLink(otp)) {
//            // Complete sign-in
//            auth.signInWithEmailLink(email, otp)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        // User signed in successfully
//                        Toast.makeText(context, "Email verified successfully", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(context, "Error verifying OTP: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//        } else {
//            Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
//        }
//    }


    @Composable
    fun authLauncher(
        onAuthComplete: (AuthResult) -> Unit,
        onAuthError: (ApiException) -> Unit
    ) : ManagedActivityResultLauncher<Intent, ActivityResult> {
        val scope = rememberCoroutineScope()
        return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try{
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!,null)
                scope.launch {
                    val authResult = com.google.firebase.Firebase.auth.signInWithCredential(credential).await()
                    onAuthComplete(authResult)
                }
            }catch(e: ApiException){
                onAuthError(e)
            }
        }
    }

    fun logout() {
        auth.signOut()
        _firebaseUser.postValue(null)

    }
}

