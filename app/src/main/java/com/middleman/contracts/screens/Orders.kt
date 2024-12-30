package com.middleman.contracts.screens

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddBox
import androidx.compose.material.icons.rounded.AddTask
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.DonutLarge
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.middleman.contracts.model.OrderModel
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.PrimaryColor
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.ui.theme.ubuntuFontFamily
import com.middleman.contracts.viewmodel.CreatedOrdersViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Orders(
    viewModel: CreatedOrdersViewModel,
    userId: String,
    navController: NavHostController,
    sellerEmail: String,
    customerEmail: String
) {
    val sellOrders = remember { mutableStateListOf<OrderModel>() }
    val buyOrders = remember { mutableStateListOf<OrderModel>() }
    val isLoading = remember { mutableStateOf(true) }
    var isBuyOrdersSelected by remember { mutableStateOf(false) }
    var isSellOrdersSelected by remember { mutableStateOf(true) }
    var showPayButton by remember { mutableStateOf(false) }

    viewModel.fetchOrdersByCustomerEmailId(customerEmail) { fetchedOrders ->
        buyOrders.clear()
        buyOrders.addAll(fetchedOrders)
        isLoading.value = false
    }

    viewModel.fetchOrdersBySellerEmailId(sellerEmail) { fetchedOrders ->
        sellOrders.clear()
        sellOrders.addAll(fetchedOrders)
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
                                    navController.navigate(Routes.BottomNav.routes)
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
                            text = "All Orders",
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
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .size(160.dp, 55.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (isSellOrdersSelected) Color.Black else Color.LightGray)
                        .clickable {
                            isSellOrdersSelected = true
                            isBuyOrdersSelected = false
                            showPayButton = false
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sell Orders",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = if (isSellOrdersSelected) Color.White else Color.Black,
                        fontFamily = ubuntuFontFamily
                    )
                }

                Box(
                    modifier = Modifier
                        .size(160.dp, 55.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (isBuyOrdersSelected) Color.Black else Color.LightGray)
                        .clickable {
                            isBuyOrdersSelected = true
                            isSellOrdersSelected = false
                            showPayButton = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Buy Orders",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = if (isBuyOrdersSelected) Color.White else Color.Black,
                        fontFamily = ubuntuFontFamily
                    )
                }
            }

            if (isBuyOrdersSelected) {
                if (isLoading.value) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Text(
                            text = "Loading orders...",
                            color = Color.Black,
                            fontFamily = poppinsFontFamily,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .wrapContentSize(Alignment.Center)
                        )
                    }
                } else if (buyOrders.isEmpty()) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No Orders Available",
                                color = Color.Black,
                                fontFamily = poppinsFontFamily,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentSize(Alignment.Center)
                            )

                            Button(
                                onClick = {
                                    navController.navigate(Routes.CreateOrder.routes)
                                },
                                colors = ButtonDefaults.buttonColors(Color.Black),
                                modifier = Modifier
                                    .height(50.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "New Order",
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(horizontal = 3.dp)
                                    )
                                    Icon(
                                        Icons.Rounded.AddBox,
                                        contentDescription = "Arrow Icon",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                } else {
                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp, bottom = 4.dp)) {
                        items(buyOrders.reversed()) { order ->
                            DetailedOrderItem(
                                sellerName = order.seller,
                                productName = order.productName,
                                totalAmount = order.totalAmount,
                                customerName = order.customer,
                                productCost = order.productCost,
                                productQuantity = order.productQuantity,
                                time = order.timeStamp,
                                navController = navController,
                                orderKey = order.orderKey,
                                showPayButton = showPayButton
                            )
                        }
                    }
                }
            } else {
                if (isLoading.value) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Text(
                            text = "Loading orders...",
                            color = Color.Black,
                            fontFamily = poppinsFontFamily,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .wrapContentSize(Alignment.Center)
                        )
                    }
                } else if (sellOrders.isEmpty()) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No Orders Available",
                                color = Color.Black,
                                fontFamily = poppinsFontFamily,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentSize(Alignment.Center)
                            )

                            Button(
                                onClick = {
                                    navController.navigate(Routes.CreateOrder.routes)
                                },
                                colors = ButtonDefaults.buttonColors(Color.Black),
                                modifier = Modifier
                                    .height(50.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "New Order",
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(horizontal = 3.dp)
                                    )
                                    Icon(
                                        Icons.Rounded.AddBox,
                                        contentDescription = "Arrow Icon",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                } else {
                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp, bottom = 4.dp)) {
                        items(sellOrders.reversed()) { order ->
                            DetailedOrderItem(
                                sellerName = order.seller,
                                productName = order.productName,
                                totalAmount = order.totalAmount,
                                customerName = order.customer,
                                productCost = order.productCost,
                                productQuantity = order.productQuantity,
                                time = order.timeStamp,
                                navController = navController,
                                orderKey = order.orderKey,
                                showPayButton = showPayButton
                            )
                        }
                    }
                }
            }


        }
    }
}


@Composable
fun DetailedOrderItem(
    sellerName: String,
    productName: String,
    totalAmount: String,
    customerName: String,
    productCost: String,
    productQuantity: String,
    time: String,
    orderKey: String, // Add orderKey parameter
    navController: NavHostController,
    showPayButton : Boolean
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("${Routes.OrderDetails.routes}/$orderKey")
            }, horizontalAlignment = Alignment.Start) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Rounded.DonutLarge,
                contentDescription = "bullet",
                tint = PrimaryColor
            )

            Column {
                Text(
                    text = "Product: $productName",
                    fontFamily = ubuntuFontFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
                )
                val timeAndDate = try {
                    formatTimestamp(time.toLong())
                } catch (e: NumberFormatException) {
                    "Invalid date"
                }
                Text(
                    text = "Seller: $sellerName",
                    fontFamily = poppinsFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
                )
                Text(
                    text = "Buyer: $customerName",
                    fontFamily = poppinsFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        Text(
                            text = "Cost per unit: $productCost",
                            fontFamily = poppinsFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.Gray
                        )
                        Text(
                            text = "Product Quantity: $productQuantity",
                            fontFamily = poppinsFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.Gray
                        )
                        Text(
                            text = "Payment Amount: $totalAmount",
                            fontFamily = poppinsFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.Gray
                        )
                    }

                    if(showPayButton) {
                        Button(
                            modifier = Modifier.height(35.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff05750a)),
                            onClick = {
                                navController.navigate("${Routes.OrderDetails.routes}/$orderKey")
                            }
                        ) {
                            Text("Pay", fontFamily = poppinsFontFamily, color = Color.White)
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 7.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }
        }

    }
}



fun formatTimestamp(timestamp: Long): String {
//     Create a Date object from the timestamp
    val date = Date(timestamp)

    // Create a SimpleDateFormat instance to format the date
    val dateFormat = SimpleDateFormat("dd-MM-yyyy | HH:mm:ss", Locale.getDefault())

    // Format the date and return it as a string
    return dateFormat.format(date)
}