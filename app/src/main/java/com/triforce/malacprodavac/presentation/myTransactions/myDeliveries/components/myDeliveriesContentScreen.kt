package com.triforce.malacprodavac.presentation.myTransactions.myDeliveries.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.presentation.myTransactions.myDeliveries.MyDeliveriesViewModel
import com.triforce.malacprodavac.util.enum.OrderStatus

@Composable
fun MyDeliveriesContentScreen(
    navController: NavController,
    padding: PaddingValues,
    viewModel: MyDeliveriesViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier.padding(padding)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.state.orders.size) {
                if (viewModel.state.orders[it].orderStatus != OrderStatus.Received.toString())
                    MyDeliveriesRow(navController, viewModel, viewModel.state.orders[it])
            }
        }
    }
}