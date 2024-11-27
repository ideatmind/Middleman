package com.middleman.contracts.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddTask
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.DonutLarge
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.middleman.contracts.model.OrderModel
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.ui.theme.ubuntuFontFamily
import com.middleman.contracts.viewmodel.CreatedOrdersViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Orders(viewModel: CreatedOrdersViewModel, userId: String, navController: NavHostController) {
    val orders = remember { mutableStateListOf<OrderModel>() }
    val isLoading = remember { mutableStateOf(true) }

    viewModel.fetchOrdersByUserId(userId) { fetchedOrders ->
        orders.clear()
        orders.addAll(fetchedOrders)
        isLoading.value = false // Set loading to false after fetching
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
        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            item {
                if (isLoading.value) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Loading orders...",
                            color = Color.Black,
                            fontFamily = poppinsFontFamily,
                            modifier = Modifier.fillMaxSize().padding(16.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Now add the orders directly in the same LazyColumn
            items(orders.reversed()) { order ->
                DetailedOrderItem(
                    sellerName = order.seller,
                    productName = order.productName,
                    totalAmount = order.totalAmount,
                    customerName = order.customer,
                    productCost = order.productCost,
                    productQuantity = order.productQuantity,
                    time = order.timeStamp
                )
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
    time: String
) {
    Column(Modifier.fillMaxWidth().clickable {

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
                contentDescription = "bullet"
            )

            Column {
                Row {
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
                        text = "( $timeAndDate )",
                        fontFamily = poppinsFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color.Gray
                    )
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
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 35.dp, vertical = 7.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
    }
}



fun formatTimestamp(timestamp: Long): String {
    // Create a Date object from the timestamp
    val date = Date(timestamp)

    // Create a SimpleDateFormat instance to format the date
    val dateFormat = SimpleDateFormat("dd-MM-yyyy | HH:mm:ss", Locale.getDefault())

    // Format the date and return it as a string
    return dateFormat.format(date)
}