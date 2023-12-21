package com.triforce.malacprodavac.presentation.myTransactions.myPurchases

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.repository.OrderRepository
import com.triforce.malacprodavac.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPurchasesViewModel @Inject constructor(

    private val repository: OrderRepository

) : ViewModel() {

    var state by mutableStateOf(MyPurchasesState())

    init {
        getOrders()
    }

    fun onEvent(event: MyPurchasesEvent) {
        when (event) {
            is MyPurchasesEvent.DeleteOrder -> deleteOrder(event.orderId)
        }
    }

    private fun getOrders() {
        viewModelScope.launch {
            repository.getOrders(true).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            state = state.copy(orders = result.data)
                        }
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun deleteOrder(orderId: Int) {
        viewModelScope.launch {
            repository.deleteOrder(orderId).collect { result ->
                when (result) {
                    is Resource.Success -> updateOrdersAfterDeletion(orderId)
                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun updateOrdersAfterDeletion(orderId: Int) {
        val updatedList = state.orders.toMutableList()
        updatedList.removeIf { it.id == orderId }
        state = state.copy(orders = updatedList)
    }

    private fun handleError() {}

    private fun handleLoading(isLoading: Boolean) {
        state = state.copy(isLoading = isLoading)
    }
}