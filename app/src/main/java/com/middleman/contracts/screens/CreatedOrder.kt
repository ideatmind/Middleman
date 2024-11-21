//package com.middleman.contracts.screens
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.middleman.contracts.model.OrderModel
//import com.middleman.contracts.navigation.Routes
//import com.middleman.contracts.ui.theme.poppinsFontFamily
//import com.middleman.contracts.ui.theme.ubuntuFontFamily
//import com.middleman.contracts.viewmodel.CreatedOrdersViewModel
//
//@Composable
//fun CreatedOrder(
//    navHostController: NavHostController,
//    orderId: String
//) {
//    // State to control the visibility of the dialog
//    val showDialog = remember { mutableStateOf(true) } // Set to true to show the dialog initially
//    val createdOrdersViewModel: CreatedOrdersViewModel = viewModel()
//    val orderDetails by createdOrdersViewModel.getOrderById(orderId).observeAsState(null)
//
//    // AlertDialog to display order details
//    if (showDialog.value) {
//        AlertDialog(
//            onDismissRequest = {
//                showDialog.value = false
//                navHostController.navigate(Routes.BottomNav.routes)
//            },
//            title = {
//                Text(
//                    text = "Your Order",
//                    color = Color.Black,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    fontFamily = ubuntuFontFamily
//                )
//            },
//            text = {
//                if (orderDetails != null) {
//                    ShowOrder(orderDetails!!)
//                } else {
//                    Text(
//                        text = "Loading order details...",
//                        fontSize = 18.sp,
//                        modifier = Modifier.padding(16.dp)
//                    )
//                }
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        navHostController.navigate(Routes.BottomNav.routes)
//                        showDialog.value = false // Close the dialog when "Ok" is clicked
//                    },
//                    colors = ButtonDefaults.buttonColors(Color.Black)
//                ) {
//                    Text("Ok", color = Color.White, fontFamily = ubuntuFontFamily)
//                }
//            }
//        )
//    }
//}
//
//@Composable
//fun ShowOrder(orderDetails: OrderModel) {
//    Box {
//        Column(Modifier.padding(4.dp)) {
//            Text(
//                text = "Seller: ${orderDetails.seller}",
//                color = Color.Black,
//                fontSize = 15.sp,
//                fontWeight = FontWeight.Normal,
//                fontFamily = poppinsFontFamily,
//                modifier = Modifier.padding(1.dp)
//            )
//            Text(
//                text = "Customer: ${orderDetails.customer}",
//                color = Color.Black,
//                fontSize = 15.sp,
//                fontWeight = FontWeight.Normal,
//                fontFamily = poppinsFontFamily,
//                modifier = Modifier.padding(1.dp)
//            )
//            Text(
//                text = "Product Name: ${orderDetails.productName}",
//                color = Color.Black,
//                fontSize = 15.sp,
//                fontWeight = FontWeight.Normal,
//                fontFamily = poppinsFontFamily,
//                modifier = Modifier.padding(1.dp)
//            )
//            Text(
//                text = "Product Cost: ${orderDetails.productCost}",
//                color = Color.Black,
//                fontSize = 15.sp,
//                fontWeight = FontWeight.Normal,
//                fontFamily = poppinsFontFamily,
//                modifier = Modifier.padding(1.dp)
//            )
//            Text(
//                text = "Product Quantity: ${orderDetails.productQuantity}",
//                color = Color.Black,
//                fontSize = 15.sp,
//                fontWeight = FontWeight.Normal,
//                fontFamily = poppinsFontFamily,
//                modifier = Modifier.padding(1.dp)
//            )
//            Text(
//                text = "Total Amount: ${orderDetails.totalAmount}",
//                color = Color.Black,
//                fontSize = 15.sp,
//                fontWeight = FontWeight.Normal,
//                fontFamily = poppinsFontFamily,
//                modifier = Modifier.padding(1.dp)
//            )
//        }
//    }
//}
