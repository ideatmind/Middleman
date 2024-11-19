package com.middleman.contracts.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.ui.theme.ubuntuFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)) // Dark background
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
                    text = "Register",
                    fontWeight = FontWeight.Bold,
                    fontFamily = ubuntuFontFamily,
                    fontSize = 32.sp,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )

                // Container for input fields
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2A2A2A), shape = RoundedCornerShape(16.dp)) // Darker gray container
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Username Field
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Username", color = Color.White) },
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

                        Spacer(modifier = Modifier.height(8.dp))

// Email Field
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email", color = Color.White) },
                            leadingIcon = {
                                Icon(Icons.Outlined.Email, contentDescription = "Email Icon", tint = Color.White)
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

                        Spacer(modifier = Modifier.height(8.dp))

// OTP Field
                        OutlinedTextField(
                            value = otp,
                            onValueChange = { otp = it },
                            label = { Text("OTP", color = Color.White) },
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

                        Spacer(modifier = Modifier.height(8.dp))

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


                        Spacer(modifier = Modifier.height(14.dp))
                        // Sign Up Button
                        Button(
                            onClick = { /* Handle sign up action */ },
                            colors = ButtonDefaults.buttonColors(Color.White), // Light purple button color
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Sign up", color = Color.Black, fontSize = 20.sp, fontFamily = poppinsFontFamily, fontWeight = FontWeight.SemiBold)
                                Icon(Icons.Default.ArrowForwardIos, contentDescription = "Arrow Icon", tint = Color.Black)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Terms of Service Text
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Already have an account?", color = Color.White, fontSize = 14.sp)
                            TextButton(onClick = {
                                navController.navigate(Routes.Login.routes) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }) {
                                Text("Login here",
                                    fontSize = 14.sp,
                                    color = Color(0xFF64B5F6) // Light blue for emphasis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
