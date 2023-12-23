package com.triforce.malacprodavac.presentation.myTransactions.transactionDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.presentation.myTransactions.transactionDetails.TransactionDetailsViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_GreenDark
import com.triforce.malacprodavac.ui.theme.MP_Orange_Dark
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White
import com.triforce.malacprodavac.util.enum.DeliveryMethod

@Composable
fun TransactionDetailsContentScreen(
    padding: PaddingValues,
    viewModel: TransactionDetailsViewModel = hiltViewModel()
) {
    val order = viewModel.state.order
    val user = viewModel.state.user

    if (order != null && user != null) {

        val totalPrice = String.format("%.2f", order.product?.price!! * order.quantity)

        val date: String = order.updatedAt.split("T")[0]
        val time: String = order.updatedAt.split("T")[1].split(".")[0]

        val statusMap = mapOf(
            "Ordered" to "Na čekanju",
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

        Box(modifier = Modifier.fillMaxSize()) {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(padding)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = MP_Black,
                        shape = RoundedCornerShape(3.5.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(MP_White)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxSize(0.85F)
                    .padding(10.dp)
                    .align(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = order.product.title,
                        style = MaterialTheme.typography.h3,
                        color = MP_Orange_Dark,
                        fontWeight = FontWeight.W400
                    )

                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(
                        text = "Dostava: " + orderDeliveryMethod,
                        style = MaterialTheme.typography.body1,
                        color = MP_Black,
                        fontWeight = FontWeight.W300
                    )
                    if (order.deliveryMethod == DeliveryMethod.ByCourier.toString()) {
                        Text(
                            text = "Kurir: " + order.courier?.user?.firstName + " " + order.courier?.user?.lastName,
                            style = MaterialTheme.typography.body1,
                            color = MP_Black,
                            fontWeight = FontWeight.W300
                        )
                        Text(
                            text = "Kontakt: " + order.courier?.user?.phoneNumber,
                            style = MaterialTheme.typography.body1,
                            color = MP_Black,
                            fontWeight = FontWeight.W300
                        )
                    }

                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(
                        text = "Status: " + orderStatus,
                        style = MaterialTheme.typography.h6,
                        color = MP_GreenDark,
                        fontWeight = FontWeight.W400
                    )

                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(
                        text = "${order.quantity} ${order.product.unitOfMeasurement}",
                        style = MaterialTheme.typography.body1,
                        color = MP_Black,
                        fontWeight = FontWeight.W300
                    )
                    Text(
                        text = "Ukupno: ${totalPrice} ${order.product.currency}",
                        style = MaterialTheme.typography.h6,
                        color = MP_Black,
                        fontWeight = FontWeight.W300
                    )

                    Spacer(modifier = Modifier.padding(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.logo_green),
                            contentDescription = "DeleteOrder",
                            tint = MP_Pink,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Text(
                            text = "${date} ${time}",
                            style = MaterialTheme.typography.body2,
                            color = MP_Pink,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}