package com.middleman.contracts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DonutLarge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.middleman.contracts.model.OrderModel
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.ui.theme.CardColor
import com.middleman.contracts.ui.theme.poppinsFontFamily
import com.middleman.contracts.ui.theme.ubuntuFontFamily
import com.middleman.contracts.viewmodel.CreatedOrdersViewModel

@Composable
fun LiveOrders(viewModel: CreatedOrdersViewModel, userId: String, navController: NavHostController) {
    val orders = remember { mutableStateListOf<OrderModel>() }
    val isLoading = remember { mutableStateOf(true) } // Loading state

    // Fetch orders by user ID
    viewModel.fetchOrdersByUserId(userId) { fetchedOrders ->
        orders.clear()
        orders.addAll(fetchedOrders)
        isLoading.value = false // Set loading to false after fetching
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color(0xFFEFEFEF)) // Light gray background
    ) {
        Box() {
            if (isLoading.value) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Loading orders...",
                        color = Color(0xFF333333), // Darker text color
                        fontFamily = poppinsFontFamily,
                        modifier = Modifier.fillMaxSize().padding(16.dp).wrapContentSize(Alignment.Center)
                    )
                }
            } else {
                if (orders.isEmpty()) {
                    // Display a message or a placeholder when the list is empty
                    Text(
                        text = "No orders available",
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center), // Center the text
                        color = Color(0xFF888888), // Gray color for empty state
                        fontSize = 20.sp // Optional: Adjust font size
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(CardColor) // Light gray background
                    ) {
                        items(orders.reversed()) { order ->
                            OrderItem(
                                sellerName = order.seller,
                                customerName = order.customer,
                                productName = order.productName,
                                totalAmount = order.totalAmount,
                                navHostController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItem(
    sellerName: String,
    customerName: String,
    productName: String,
    totalAmount: String,
    navHostController: NavHostController
) {
    Column(Modifier.fillMaxWidth().clickable {
        navHostController.navigate(Routes.Orders.routes)
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
                tint = Color(0xFF6200EE) // Primary color for icons
            )

            Column {
                Text(
                    text = "Product: $productName",
                    fontFamily = ubuntuFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color(0xFF333333) // Darker text color
                )
                Text(
                    text = "Seller: $sellerName",
                    fontFamily = poppinsFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color(0xFF555555) // Medium gray for seller name
                )
                Text(
                    text = "Customer: $customerName",
                    fontFamily = poppinsFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color(0xFF555555) // Medium gray for customer name
                )
                Text(
                    text = "Payment Amount: $totalAmount",
                    fontFamily = poppinsFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color(0xFF888888) // Light gray for amount
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 35.dp, vertical = 7.dp),
            thickness = 1.dp,
            color = Color(0xFFDDDDDD) // Light gray for divider
        )
    }
}
