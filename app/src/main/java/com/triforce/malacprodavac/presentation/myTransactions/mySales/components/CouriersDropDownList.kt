package com.triforce.malacprodavac.presentation.myTransactions.mySales.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.triforce.malacprodavac.domain.model.Courier
import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.presentation.myTransactions.mySales.MySalesEvent
import com.triforce.malacprodavac.presentation.myTransactions.mySales.MySalesViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_White

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouriersDropDownList(
    viewModel: MySalesViewModel,
    order: Order,
    couriers: List<Courier> = emptyList(),
    selectedCourier: String? = null,
    handleSelect: (Any) -> Unit,
    label: String,
    fill: Boolean
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selected by if (selectedCourier != null)
        remember { mutableStateOf(selectedCourier) }
    else mutableStateOf("")

    Column(
        modifier = if (fill) {
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10.dp))
        } else {
            Modifier.width(150.dp)
        }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            color = MP_Black,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
        )
        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {
            isExpanded = it
        }) {
            TextField(
                value = selected,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .clip(RoundedCornerShape(10.dp)),
                readOnly = true,
                onValueChange = { },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = isExpanded, onDismissRequest = {
                    isExpanded = false
                }, modifier = Modifier
                    .fillMaxWidth()
                    .background(MP_White)
            ) {
                couriers.forEach { courier ->
                    DropdownMenuItem(
                        text = {
                            Column {
                                Text(
                                    text = "${courier.user?.firstName} ${courier.user?.lastName}",
                                    color = MP_Black,
                                    style = MaterialTheme.typography.body1,
                                )
                                Text(
                                    text = courier.user?.phoneNumber ?: "",
                                    color = MP_Black,
                                    style = MaterialTheme.typography.caption
                                )
                            }
                        },
                        onClick = {
                            selected = "${courier.user?.firstName} ${courier.user?.lastName}"
                            isExpanded = false
                            handleSelect(courier)
                            viewModel.onEvent(MySalesEvent.CourierIdChanged(courier.id, order.id))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                }
            }
        }
    }
}