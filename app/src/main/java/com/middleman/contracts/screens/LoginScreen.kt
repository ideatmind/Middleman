package com.middleman.contracts.screens
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.middleman.contracts.R
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.ui.theme.ubuntuFontFamily
import com.middleman.contracts.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }

    // Handle back button press
    val activity = LocalContext.current as? Activity
    BackHandler {
        // Show the confirmation dialog when back is pressed
        showDialog = true
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Close App", fontFamily = ubuntuFontFamily, fontSize = 19.sp, fontWeight = FontWeight.SemiBold) },
            text = { Text("Do you want to close the app?", fontFamily = poppinsFontFamily, fontSize = 15.sp, fontWeight = FontWeight.Normal) },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                },
                    colors = ButtonDefaults.buttonColors(Color.Black)) {
                    Text("No", fontFamily = poppinsFontFamily)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    activity?.finishAffinity() // Close the app
                }) {
                    Text("Yes", fontFamily = poppinsFontFamily)
                }
            }
        )
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState()
    val error by authViewModel.error.observeAsState()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    // Google login
    val launcher = authViewModel.authLauncher(
        onAuthComplete = { result ->
            user = result.user
        },
        onAuthError = {
            user = null
        }
    )

    val token = stringResource(R.string.web_id)

    val context = LocalContext.current


    LaunchedEffect(firebaseUser) {
        if(firebaseUser !=  null) {
            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(user) {
        if(user != null) {
            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
            Toast.makeText(context, "Logging in with ${user!!.email}", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        // Orange Triangle
        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(y = (-30).dp, x = (-30).dp)
                .background(color = Color(0xFFFFA500), shape = RoundedCornerShape(bottomEnd = 200.dp))
        )

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center,
               modifier =  Modifier.fillMaxWidth().background(Color(0xFF121212))) {
                Card(
                    modifier = Modifier.size(80.dp).background(Color(0xFF121212)),
                    shape = RoundedCornerShape(100.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ribbon),
                            contentDescription = "Ribbon Icon",
                            modifier = Modifier.size(65.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)), // Darker card color
            border = BorderStroke(0.7.dp, Color.White), // Light purple border
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.graphicsLayer {
                shadowElevation = 8.dp.toPx() // Adding shadow for depth
                shape = RoundedCornerShape(16.dp)
                clip = true
            }
        ) {
            // Title
            Text(
                text = "Login",
                fontWeight = FontWeight.Bold,
                fontFamily = ubuntuFontFamily,
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )

            // Green Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF262626), shape = RoundedCornerShape(16.dp)) // Darker gray container
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    // Username Field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = Color.White) },
                        leadingIcon = {
                            Icon(Icons.Outlined.Person, contentDescription = "Username Icon", tint = Color.White)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            cursorColor = Color.White,
                            disabledLeadingIconColor = Color.Gray,
                            focusedLeadingIconColor = Color.White,
                            focusedPlaceholderColor = Color.White,
                            unfocusedPlaceholderColor = Color(0xFFB0B0B0), // Darker placeholder
                            disabledTextColor = Color.LightGray,
                            errorTextColor = Color.Red,
                            unfocusedLeadingIconColor = Color(0xFFB0B0B0), // Darker icon
                            focusedBorderColor = Color.White,
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", color = Color.White) },
                        leadingIcon = {
                            Icon(Icons.Outlined.Lock, contentDescription = "Password Icon", tint = Color.White)
                        },
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            val icon = if (passwordVisible) {
                                Icons.Outlined.Visibility // Show password icon
                            } else {
                                Icons.Outlined.VisibilityOff // Hide password icon
                            }
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(icon, contentDescription = if (passwordVisible) "Hide password" else "Show password", tint = Color.LightGray)
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            cursorColor = Color.White,
                            disabledLeadingIconColor = Color.Gray,
                            focusedLeadingIconColor = Color.White,
                            focusedPlaceholderColor = Color.White,
                            unfocusedPlaceholderColor = Color(0xFFB0B0B0), // Darker placeholder
                            disabledTextColor = Color.LightGray,
                            errorTextColor = Color.Red,
                            unfocusedLeadingIconColor = Color(0xFFB0B0B0), // Darker icon
                            focusedBorderColor = Color.White,
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Forgot Password Text
                    TextButton (onClick = {
                        navController.navigate(Routes.ForgotPassword.routes)
                    }, Modifier.align(Alignment.End)) {
                        Text("Forgot password?",
                            modifier = Modifier
                                .padding(top = 4.dp),
                            color = Color(0xFF64B5F6),
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    // Login Button
                    Button(
                        onClick = {
                            error?.let {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                            if(email.isEmpty() || password.isEmpty()) {
                                Toast.makeText(context, "Provide all details", Toast.LENGTH_SHORT).show()
                            }else{
                                authViewModel.login(email, password, context)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.White), // Orange button color
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Login", color = Color.Black, fontSize = 20.sp, fontFamily = poppinsFontFamily, fontWeight = FontWeight.SemiBold)
                            Icon(Icons.Default.ArrowForwardIos, contentDescription = "Arrow Icon", tint = Color.Black)
                        }
                    }

                    HorizontalDivider(Modifier.padding(vertical = 20.dp, horizontal = 24.dp), color = Color.LightGray)

                    Button(onClick = {
                        if(user == null) {
                            val gso =
                                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(token)
                                    .requestEmail()
                                    .build()
                            val gsc = GoogleSignIn.getClient(context,gso)
                            launcher.launch(gsc.signInIntent)
                        }
                    },
                        colors = ButtonDefaults.buttonColors(Color.White), // Orange button color
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Login with Google", color = Color.Black, fontSize = 17.sp, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Normal)

                            Spacer(modifier = Modifier.width(10.dp))

                            Image(
                                painter = painterResource(id = R.drawable.google_icon),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Terms of Service Text
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Don't have an account!", color = Color.White, fontSize = 14.sp)
                        TextButton (onClick = {
                            navController.navigate(Routes.Register.routes) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }){
                            Text("Register here",
                                fontSize = 14.sp,
                                color = Color(0xFF64B5F6)
                            )
                        }
                    }
                }
            }
        }
        }
    }
}