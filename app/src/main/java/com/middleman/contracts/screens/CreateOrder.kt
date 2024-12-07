package com.middleman.contracts.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocalPhone
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.rounded.AddBox
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.ProductionQuantityLimits
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.middleman.contracts.model.NotificationModel
import com.middleman.contracts.model.OrderModel
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.ui.theme.ubuntuFontFamily
import com.middleman.contracts.utils.SharedPref
import com.middleman.contracts.viewmodel.AddOrderViewModel
import com.middleman.contracts.viewmodel.AuthViewModel
import com.middleman.contracts.viewmodel.CreatedOrdersViewModel
import com.middleman.contracts.viewmodel.NotificationViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrder(
    navController: NavHostController,
    notificationViewModel: NotificationViewModel = viewModel()
) {

    BackHandler {
        navController.navigate(Routes.BottomNav.routes) {
            popUpTo(Routes.BottomNav.routes) {
                inclusive = true
            }
        }
    }

    val context = LocalContext.current

    val orderViewModel: AddOrderViewModel = viewModel()
    val isPosted by orderViewModel.isPosted.observeAsState(false)

    val sellerName by remember { mutableStateOf(SharedPref.getUserName(context)) }
    var sellerPhone by remember { mutableStateOf(SharedPref.getPhone(context)) }
    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var productCost by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var orderId by remember { mutableStateOf("") }
    val sellerEmail by remember { mutableStateOf(SharedPref.getEmail(context)) }
    var customerEmail by remember { mutableStateOf("") }



    LaunchedEffect(isPosted) {
        if (isPosted) {
            customerEmail = ""
            customerPhone = ""
            sellerPhone = ""
            customerName = ""
            productName = ""
            productQuantity = ""
            productCost = ""
            totalAmount = ""

            Toast.makeText(context, "Order created", Toast.LENGTH_SHORT).show()

            // Get the order key
            orderId = orderViewModel.orderKey.value ?: ""
            showDialog = true // Show the dialog
        }
    }


    fun encodeEmail(email: String): String {
        return email.replace(".", "_") // Replace '.' with '_'
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create Order",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = ubuntuFontFamily
                    )
                },
                navigationIcon = {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Arrow Icon",
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            navController.navigate(Routes.BottomNav.routes) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp), // Add padding to the column
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Space between each item
                ) {
                    // Customer Name and Phone
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = customerName,
                            onValueChange = { customerName = it },
                            label = { Text("Customer Name", color = Color.DarkGray) },
                            leadingIcon = {
                                Icon(Icons.Outlined.Person, contentDescription = "Customer Icon", tint = Color.Black)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp), // Use weight for equal distribution
                            shape = RoundedCornerShape(24.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedTextColor = Color.Black,
                                focusedTextColor = Color.Black,
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black
                            )
                        )

                        OutlinedTextField(
                            value = customerPhone,
                            onValueChange = {
                                if (it.length <= 10) customerPhone = it else Toast.makeText(
                                    context,
                                    "Only 10 characters allowed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            label = { Text("Customer Phone", color = Color.DarkGray) },
                            leadingIcon = {
                                Icon(Icons.Outlined.LocalPhone, contentDescription = "Phone Icon", tint = Color.Black)
                            },
                            modifier = Modifier.weight(1f), // Use weight for equal distribution
                            shape = RoundedCornerShape(24.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedTextColor = Color.Black,
                                focusedTextColor = Color.Black,
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black
                            )
                        )
                    }

                    // Customer Email
                    OutlinedTextField(
                        value = customerEmail,
                        onValueChange = { customerEmail = it },
                        label = { Text("Customer Email Id", color = Color.DarkGray) },
                        leadingIcon = {
                            Icon(Icons.Outlined.Email, contentDescription = "Email Icon", tint = Color.Black)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black
                        )
                    )

                    // Product Name
                    OutlinedTextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = { Text("Product Name", color = Color.DarkGray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black
                        )
                    )

                    var cost: Double = 0.0
                    var quantity = 0
                    OutlinedTextField(
                        value = productCost,
                        onValueChange = {
                            productCost = it
                            cost = productCost.toDoubleOrNull() ?: 0.0
                            quantity = productQuantity.toIntOrNull() ?: 0
                            totalAmount = (cost * quantity).toString()
                        },
                        label = { Text("Single Product Cost", color = Color.DarkGray) },
                        leadingIcon = {
                            Icon(Icons.Outlined.AttachMoney, contentDescription = "Cost Icon", tint = Color.Black)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black
                        )
                    )

                    // Product Quantity
                    OutlinedTextField(
                        value = productQuantity,
                        onValueChange = {
                            productQuantity = it
                            // Calculate the total amount when the quantity changes
                            cost = productCost.toDoubleOrNull() ?: 0.0
                            quantity = productQuantity.toIntOrNull() ?: 0
                            totalAmount = (cost * quantity).toString()
                        },
                        label = { Text("Product Quantity", color = Color.DarkGray) },
                        leadingIcon = {
                            Icon(Icons.Rounded.ProductionQuantityLimits, contentDescription = "Quantity Icon", tint = Color.Black)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black
                        )
                    )

                    // Total Amount (Read-only)
                    OutlinedTextField(
                        value = totalAmount,
                        onValueChange = {}, // No-op since it's uneditable
                        label = { Text("Total Amount", color = Color.DarkGray) },
                        leadingIcon = {
                            Icon(Icons.Rounded.Money, contentDescription = "Total Amount Icon", tint = Color.Black)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        enabled = false // Make the total amount field uneditable
                    )

                    // Create Order Button
                    Button(
                        onClick = {
                            when {
                                sellerName.isEmpty() -> Toast.makeText(
                                    context,
                                    "Seller name is empty",
                                    Toast.LENGTH_SHORT
                                ).show()

                                customerName.isEmpty() -> Toast.makeText(
                                    context,
                                    "Customer name is empty",
                                    Toast.LENGTH_SHORT
                                ).show()

                                productName.isEmpty() -> Toast.makeText(
                                    context,
                                    "Product Name is empty",
                                    Toast.LENGTH_SHORT
                                ).show()

                                productCost.isEmpty() -> Toast.makeText(
                                    context,
                                    "Product Cost is empty",
                                    Toast.LENGTH_SHORT
                                ).show()

                                productQuantity.isEmpty() -> Toast.makeText(
                                    context,
                                    "Quantity is empty",
                                    Toast.LENGTH_SHORT
                                ).show()

                                else -> {

                                    orderViewModel.saveData(
                                        seller = sellerName,
                                        sellerPhone = sellerPhone,
                                        customer = customerName,
                                        customerPhone = customerPhone,
                                        productName = productName,
                                        productCost = productCost,
                                        productQuantity = productQuantity,
                                        totalAmount = totalAmount,
//                                        orderKey = orderId,
                                        userId = FirebaseAuth.getInstance().currentUser!!.uid,
                                        sellerEmail = sellerEmail,
                                        customerEmail = customerEmail,
                                    )
                                    Toast.makeText(
                                        context,
                                        "Order created successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Add notification for the seller
                                    notificationViewModel.addNotification(
                                        sellerPhone,
                                        NotificationModel(
                                            notificationName = "Order Created",
                                            notificationDescription = "Your order for $productName has been created."
                                        )
                                    )

                                    // Add notification for the customer
                                    notificationViewModel.addNotification(
                                        customerPhone,
                                        NotificationModel(
                                            notificationName = "Order Confirmation",
                                            notificationDescription = "Thank you for your order of $productName."
                                        )
                                    )

                                    showDialog = true
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Create Order",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 3.dp)
                            )
                            Icon(
                                Icons.Rounded.AddBox,
                                contentDescription = "Create Order Icon",
                                tint = Color.White
                            )
                        }
                    }
                }

            }
        }
    }

    // Show the CreatedOrder dialog if showDialog is true
    if (showDialog) {
        CreatedOrder(orderId = orderId, navController = navController) {
            showDialog = false // Close the dialog when "Ok" is clicked
        }
    }
}

@Composable
fun CreatedOrder(
    orderId: String,
    navController: NavHostController,
    onDismiss: () -> Unit,
) {
    val createdOrdersViewModel: CreatedOrdersViewModel = viewModel()
    val orderDetails by createdOrdersViewModel.getOrderById(orderId).observeAsState(null)

    AlertDialog(
        onDismissRequest = {
            onDismiss() // Close the dialog when dismissed
        },
        title = {
            Text(
                text = "Your Created Order",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ubuntuFontFamily
            )
        },
        text = {
            if (orderDetails != null) {
                ShowOrder(orderDetails!!)
            } else {
                Text(
                    text = "Loading order details...",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    navController.navigate(Routes.BottomNav.routes) {
                        popUpTo(Routes.BottomNav.routes) {
                            inclusive = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text("Ok", color = Color.White, fontFamily = ubuntuFontFamily)
            }
        }
    )
}


@Composable
fun ShowOrder(orderDetails: OrderModel) {
    val ShowOrderItems = listOf(
                ShowOrderData("Order Id: ${orderDetails.orderKey}"),
                ShowOrderData("Seller: ${orderDetails.seller}"),
                ShowOrderData("Seller Phone: ${orderDetails.sellerPhone}"),
                ShowOrderData("Customer: ${orderDetails.customer}"),
                ShowOrderData("Customer Phone: ${orderDetails.customerPhone}"),
                ShowOrderData("Product Name: ${orderDetails.productName}"),
                ShowOrderData("Product Cost: ${orderDetails.productCost}"),
                ShowOrderData("Product Quantity: ${orderDetails.productQuantity}"),
                ShowOrderData("Total Amount: ${orderDetails.totalAmount}")
    )

    Box(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 1.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier.padding( vertical = 16.dp, horizontal = 2.dp), // Padding inside the Box
            verticalArrangement = Arrangement.spacedBy(8.dp) // Space between items
        ) {
            ShowOrderItems.forEach { item ->
                Text(
                    text = item.text,
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier
                        .padding(4.dp) // Padding around each text item
                        .background(
                            Color(0xFFEFEFEF),
                            shape = RoundedCornerShape(4.dp)
                        ) // Background for each item
                        .padding(4.dp) // Padding inside each item
                )
            }
        }
    }

}

data class ShowOrderData(
    var text: String
)

