package com.triforce.malacprodavac.presentation.myTransactions.myDeliveries.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.presentation.myTransactions.myDeliveries.MyDeliveriesEvent
import com.triforce.malacprodavac.presentation.myTransactions.myDeliveries.MyDeliveriesViewModel
import com.triforce.malacprodavac.presentation.myTransactions.mySales.components.SubmitSale
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_Orange
import com.triforce.malacprodavac.ui.theme.MP_Orange_Dark
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White
import com.triforce.malacprodavac.util.enum.OrderStatus

@Composable
fun MyDeliveriesRow(
    navController: NavController,
    viewModel: MyDeliveriesViewModel,
    order: Order,
    id: Int,
) {
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

    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .shadow(
                elevation = 5.dp,
                spotColor = MP_Black,
                shape = RoundedCornerShape(3.5.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(MP_White)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(10.dp)
        /*.clickable {
            navController.navigate(Screen.DetailsOrderScreen.route)
        }*/
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Dostava: " + orderDeliveryMethod,
                style = MaterialTheme.typography.h6,
                color = MP_Orange_Dark,
                fontWeight = FontWeight.W400
            )
            Text(
                text = "Status: " + orderStatus,
                style = MaterialTheme.typography.body2,
                color = MP_Black,
                fontWeight = FontWeight.W300
            )
            Spacer(modifier = Modifier.padding(12.dp))
            Text(
                text = "${order.quantity} X ${totalPrice} ${order.product.currency}",
                style = MaterialTheme.typography.body1,
                color = MP_Black,
                fontWeight = FontWeight.W300
            )
            Text(
                text = "${date} ${time}",
                style = MaterialTheme.typography.body1,
                color = MP_Pink,
                fontWeight = FontWeight.W400
            )
            Spacer(modifier = Modifier.padding(12.dp))

            OrderStatusDropDownList(
                orderStatusList = listOf(
                    OrderStatus.Packaged,
                    OrderStatus.InDelivery,
                    OrderStatus.Received
                ),
                viewModel = viewModel,
                order = order,
                selectedStatus = order.orderStatus,
                handleSelect = {},
                label = "Izaberi status",
                fill = true
            )

            Spacer(modifier = Modifier.padding(12.dp))
            SubmitSale(
                text = "Promeni status paketa",
                tintColor = MP_Green,
                modifier = Modifier.clickable {
                    viewModel.onEvent(MyDeliveriesEvent.Submit(order.id))
                }
            )

            Spacer(modifier = Modifier.padding(12.dp))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.logo_green),
                contentDescription = "DeleteOrder",
                tint = MP_Orange,
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}