package com.triforce.malacprodavac.presentation.myTransactions.myDeliveries

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.data.remote.orders.dto.UpdateOrderDto
import com.triforce.malacprodavac.domain.repository.CourierRepository
import com.triforce.malacprodavac.domain.repository.OrderRepository
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyDeliveriesViewModel @Inject constructor(

    private val profile: Profile,
    private val courierRepository: CourierRepository,
    private val orderRepository: OrderRepository

) : ViewModel() {

    var state by mutableStateOf(MyDeliveriesState())

    init {
        me()
    }

    fun onEvent(event: MyDeliveriesEvent) {
        when (event) {
            is MyDeliveriesEvent.OrderStatusChanged ->
                state.orders.find { it.id == event.orderId }?.apply {
                    orderStatus = event.orderStatus.toString()
                }

            is MyDeliveriesEvent.Submit ->
                state.orders.find { it.id == event.orderId }?.orderStatus?.let { orderStatus ->
                    updateOrder(event.orderId, orderStatus)
                }
        }
    }

    private fun getCourierOrders() {
        viewModelScope.launch {
            state.user?.courier?.id?.let { courierId ->
                courierRepository.getCourierOrders(courierId, true).collect { result ->
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
    }

    private fun updateOrder(orderId: Int, orderStatus: String) {
        viewModelScope.launch {
            orderRepository.updateOrder(
                orderId, UpdateOrderDto(
                    orderStatus = orderStatus,
                    accepted = null,
                    courierId = null
                )
            ).collect { result ->
                when (result) {
                    is Resource.Success -> getCourierOrders()
                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    fun me() {
        viewModelScope.launch {
            profile.getMe().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            user = result.data,
                        )

                        getToken()
                        getCourierOrders()
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun getToken() {
        profile.getToken().let {
            state = state.copy(token = it)
        }
    }

    private fun handleError() {}

    private fun handleLoading(isLoading: Boolean) {
        state = state.copy(isLoading = isLoading)
    }
}