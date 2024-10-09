package com.middleman.contracts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.middleman.contracts.ui.theme.poppinsFontFamily


//fun getGradient(
//    startColor: Color,
//    endColor: Color
//): Brush {
//    return Brush.horizontalGradient(
//        colors = listOf(startColor,endColor)
//    )
//}

@Composable
fun WalletInfo() {
    Card(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth()
            .height(160.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column() {
            Text(
                text = "Wallet",
                modifier = Modifier.padding(20.dp).padding(bottom = 15.dp),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily
            )
            Text(
                text = "Total Orders: ",
                modifier = Modifier.padding(start = 20.dp,bottom = 5.dp),
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFontFamily
            )
            Text(
                text = "Order Amount: ",
                modifier = Modifier.padding(start = 20.dp,bottom = 5.dp),
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFontFamily
            )
        }
    }
}
//data class Wallet(
//    val name: String,
//    val totalOrders: String,
//    val totalAmount: String,
//    val balance: Double,
//    val color: Brush
//)