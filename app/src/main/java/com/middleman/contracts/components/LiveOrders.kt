package com.middleman.contracts.components

import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.DonutLarge
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
            .height(900.dp)
            .padding(20.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box() {
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                ) {
                    items(orders.reversed()) { order ->
                        OrderItem(
                            sellerName = order.seller,
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


@Composable
fun OrderItem(
    sellerName: String,
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
                contentDescription = "bullet"
            )

            Column {
                Text(
                    text = "Product: $productName",
                    fontFamily = ubuntuFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
                )
                Text(
                    text = "Seller: $sellerName",
                    fontFamily = poppinsFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
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
