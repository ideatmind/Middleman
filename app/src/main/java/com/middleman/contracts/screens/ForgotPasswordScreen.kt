package com.middleman.contracts.screens
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.middleman.contracts.R
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.ui.theme.ubuntuFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
                    text = "Forgot Password?",
                    fontWeight = FontWeight.Bold,
                    fontFamily = ubuntuFontFamily,
                    fontSize = 32.sp,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )

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

                        Spacer(modifier = Modifier.height(65.dp))
                        // Login Button
                        val context = LocalContext.current
                        Button(
                            onClick = {
                                if(email.isEmpty()) {
                                    Toast.makeText(context, "Email is empty", Toast.LENGTH_SHORT).show()
                                }else {
                                    auth.sendPasswordResetEmail(email)
                                    Toast.makeText(context, "Reset link sent to $email", Toast.LENGTH_SHORT).show()
                                    navController.navigate(Routes.Login.routes) {
                                        popUpTo(Routes.Login.routes) {
                                            inclusive = true
                                        }
                                    }
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
                                Text(text = "Send password reset link", color = Color.Black, fontSize = 18.sp, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)
                                Icon(Icons.Default.ArrowForwardIos, contentDescription = "Arrow Icon", tint = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}

