package com.middleman.contracts.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddTask
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.ui.theme.ubuntuFontFamily
import com.middleman.contracts.viewmodel.CreatedOrdersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetails(
    viewModel: CreatedOrdersViewModel,
    orderKey: String,
    navController: NavHostController
) {
    val orderDetails by viewModel.getOrderById(orderKey).observeAsState(initial = null)
    val isLoading = remember { mutableStateOf(orderDetails == null) }

    if (orderDetails == null) {
        isLoading.value = true
    } else {
        isLoading.value = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Box(
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "back navigation",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    navController.navigate(Routes.Orders.routes) {
                                        popUpTo(Routes.Orders.routes) {
                                            inclusive = true
                                        }
                                    }
                                }
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Order Details",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Normal,
                            fontFamily = poppinsFontFamily
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AddTask,
                            contentDescription = "Create Order",
                            modifier = Modifier.size(25.dp),
                            tint = Color.Transparent
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading.value) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Text(
                        text = "Loading order details...",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }
            } else {
                orderDetails?.let { order ->
                    // Display the order details using the OrderDetailsItem composable
                    OrderDetailsItem(
                        seller = order.seller,
                        sellerEmail = order.sellerEmail,
                        customer = order.customer,
                        customerEmail = order.customerEmail,
                        productName = order.productName,
                        productCost = order.productCost,
                        productQuantity = order.productQuantity,
                        totalAmount = order.totalAmount,
                        userId = order.userId,
                        orderKey = order.orderKey,
                        time = order.timeStamp
                    )
                }
            }
        }
    }
}


@Composable
fun OrderDetailsItem(
    seller: String,
    sellerEmail: String,
    customer: String,
    customerEmail: String,
    productName: String,
    productCost: String,
    productQuantity: String,
    totalAmount: String,
    userId: String,
    orderKey: String,
    time: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 19.sp,
                            color = Color.Black
                        )
                    ) {
                        append("Order Id: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    ) {
                        append("( $orderKey )")
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp) // Add spacing below
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 17.sp,
                            color = Color.Black
                        )
                    ) {
                        append("Product: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    ) {
                        append(productName)
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp) // Add spacing below
            )

            val timeAndDate = try {
                formatTimestamp(time.toLong())
            } catch (e: NumberFormatException) {
                "Invalid date"
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    ) {
                        append("( Date and Time: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    ) {
                        append("$timeAndDate )")
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp) // Add spacing below
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    ) {
                        append("Seller Name:  ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 15.sp,
                            color = Color.DarkGray
                        )
                    ) {
                        append(seller)
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp) // Add spacing below
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    ) {
                        append("Customer Name:  ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 15.sp,
                            color = Color.DarkGray
                        )
                    ) {
                        append(customer)
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp) // Add spacing below
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    ) {
                        append("Seller Email:  ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 13.sp,
                            color = Color.DarkGray
                        )
                    ) {
                        append(sellerEmail)
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp) // Add spacing below
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    ) {
                        append("Customer Email:  ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = ubuntuFontFamily,
                            fontSize = 13.sp,
                            color = Color.DarkGray
                        )
                    ) {
                        append(customerEmail)
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp) // Add spacing below
            )
            Text(
                text = "Cost per unit: $productCost",
                fontFamily = poppinsFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 8.dp), // Add spacing below
                color = Color.Gray
            )
            Text(
                text = "Product Quantity: $productQuantity",
                fontFamily = poppinsFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 8.dp), // Add spacing below
                color = Color.Gray
            )
            Text(
                text = "Payment Amount: $totalAmount",
                fontFamily = poppinsFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 8.dp), // Add spacing below
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(28.dp)) // Add spacing before the button

            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp), contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier.height(35.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff05750a)),
                    onClick = {
                        // Handle button click
                    }
                ) {
                    Text("Pay $totalAmount", fontFamily = poppinsFontFamily, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(18.dp)) // Add spacing before the divider

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 7.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
        }
    }
}

