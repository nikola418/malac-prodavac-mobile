package com.triforce.malacprodavac.presentation.myTransactions.myPurchases.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.presentation.myTransactions.myPurchases.MyPurchasesEvent
import com.triforce.malacprodavac.presentation.myTransactions.myPurchases.MyPurchasesViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_GreenDark
import com.triforce.malacprodavac.ui.theme.MP_Orange_Dark
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_Pink_Dark
import com.triforce.malacprodavac.ui.theme.MP_White
import com.triforce.malacprodavac.util.enum.DeliveryMethod

@Composable
fun MyPurchasesRow(
    navController: NavController,
    viewModel: MyPurchasesViewModel,
    order: Order
) {
    var showDialog by remember { mutableStateOf(false) }

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
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = order.product.title,
                style = MaterialTheme.typography.h4,
                color = MP_Orange_Dark,
                fontWeight = FontWeight.W400
            )

            Text(
                text = "Status: " + orderStatus,
                style = MaterialTheme.typography.subtitle1,
                color = MP_GreenDark,
                fontWeight = FontWeight.W400
            )
            Spacer(modifier = Modifier.padding(12.dp))

            Text(
                text = "Dostava: " + orderDeliveryMethod,
                style = MaterialTheme.typography.subtitle1,
                color = MP_Black,
                fontWeight = FontWeight.W300
            )
            if (order.deliveryMethod == DeliveryMethod.ByCourier.toString()) {
                if (order.courier == null)
                    Text(
                        text = "Kurir nije dodeljen",
                        style = MaterialTheme.typography.body1,
                        color = MP_Black,
                        fontWeight = FontWeight.W300
                    )
                else {
                    Text(
                        text = "Kurir ${order.courier.user?.firstName} ${order.courier.user?.lastName}",
                        style = MaterialTheme.typography.body1,
                        color = MP_Black,
                        fontWeight = FontWeight.W300
                    )
                    Text(
                        text = "Kontakt: ${order.courier.user?.phoneNumber}",
                        style = MaterialTheme.typography.body1,
                        color = MP_Black,
                        fontWeight = FontWeight.W300
                    )
                }
            }
            Spacer(modifier = Modifier.padding(12.dp))
            Text(
                text = "${order.quantity} ${order.product.unitOfMeasurement} Ukupno: ${totalPrice} ${order.product.currency}",
                style = MaterialTheme.typography.body1,
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
                    tint = MP_Green,
                    modifier = Modifier
                        .size(30.dp)
                )
                Text(
                    text = "${date} ${time}",
                    style = MaterialTheme.typography.body2,
                    color = MP_Green,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "ViewMore",
            tint = MP_Black,
            modifier = Modifier
                .padding(bottom = 12.5.dp, end = 12.5.dp)
                .size(21.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    navController.navigate(Screen.TransactionDetails.route + "?id=${order.id}")
                }
        )
        Icon(
            imageVector = Icons.Default.DeleteOutline,
            contentDescription = "DeleteOrder",
            tint = MP_Black,
            modifier = Modifier
                .padding(top = 12.5.dp, end = 12.5.dp)
                .size(30.dp)
                .align(Alignment.TopEnd)
                .clickable {
                    showDialog = true
                }
        )
    }

    if (showDialog) {
        AlertDialog(
            containerColor = MP_White,
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(
                    text = "Obriši prodavca iz liste omiljenih",
                    style = MaterialTheme.typography.h5,
                    color = MP_Pink_Dark,
                    fontWeight = FontWeight.W300
                )
            },
            text = {
                Text(
                    text = "Da li ste sigurni da želite da obrišete porudžbinu za proizvod ${order.product?.title}?",
                    style = MaterialTheme.typography.body1,
                    color = MP_Black,
                    fontWeight = FontWeight.W300
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.onEvent(MyPurchasesEvent.DeleteOrder(order.id))
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MP_Green
                    )
                ) {
                    Text(
                        text = "Da",
                        style = MaterialTheme.typography.body1,
                        color = MP_White
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MP_Pink
                    )
                ) {
                    Text(
                        text = "Ne",
                        style = MaterialTheme.typography.body1,
                        color = MP_White
                    )
                }
            }
        )
    }
}