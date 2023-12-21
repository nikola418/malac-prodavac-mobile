package com.triforce.malacprodavac.presentation.myTransactions.transactionHistory.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun TransactionHistoryRow(
    navController: NavController,
    order: Order,
    id: Int,
) {
    val date: String = order.updatedAt.split("T")[0]
    val time: String = order.updatedAt.split("T")[1].split(".")[0]

    val statusMap = mapOf(
        "Ordered" to "Na čekanju...",
        "Packaged" to "Potvrđeno",
        "InDelivery" to "U isporuci",
        "Received" to "Primljeno"
    )
    val orderStatus = statusMap.getOrDefault(order.orderStatus, "Nepoznato")

    val deliveryMethodMap = mapOf(
        "ByCourier" to "Kurirska dostava",
        "SelfPickup" to "Lično preuzimanje"
    )
    val orderDeliveryMethod = deliveryMethodMap.getOrDefault(order.deliveryMethod, "Nepoznato")


    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .shadow(
                elevation = 5.dp,
                spotColor = MP_Black,
                shape = RoundedCornerShape(3.5.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(MP_White)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .requiredHeight(100.dp)
            .clickable {
                //navController.navigate(Screen.DetailsOrderScreen.route)
            }
    ) {
        Row() {

            Text(
                text = "#" + id,
                style = MaterialTheme.typography.h3,
                color = MP_Pink,
                fontWeight = FontWeight.W400
            )

            Column() {

                Text(
                    text = "${date} ${time}",
                    style = MaterialTheme.typography.body1,
                    color = MP_Black
                )
                Text(
                    text = orderDeliveryMethod,
                    style = MaterialTheme.typography.body1,
                    color = MP_Black
                )
                Text(
                    text = orderStatus,
                    style = MaterialTheme.typography.body1,
                    color = MP_Black
                )

            }
        }

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Complete",
            tint = MP_Green,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(36.dp)
        )

    }
}