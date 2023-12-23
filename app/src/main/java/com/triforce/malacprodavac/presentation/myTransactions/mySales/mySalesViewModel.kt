package com.triforce.malacprodavac.presentation.myTransactions.mySales

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.data.remote.orders.dto.UpdateOrderDto
import com.triforce.malacprodavac.domain.repository.CourierRepository
import com.triforce.malacprodavac.domain.repository.OrderRepository
import com.triforce.malacprodavac.domain.repository.ShopRepository
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import com.triforce.malacprodavac.util.enum.OrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MySalesViewModel @Inject constructor(

    private val profile: Profile,
    private val shopRepository: ShopRepository,
    private val courierRepository: CourierRepository,
    private val orderRepository: OrderRepository

) : ViewModel() {

    var state by mutableStateOf(MySalesState())

    init {
        me()
    }

    fun onEvent(event: MySalesEvent) {
        when (event) {
            is MySalesEvent.CourierIdChanged ->
                state.orders.find { it.id == event.orderId }?.apply {
                    courierId = event.courierId
                }

            is MySalesEvent.Submit ->
                state.orders.find { it.id == event.orderId }?.courierId?.let { courierId ->
                    updateOrderCourier(event.orderId, courierId)
                }

            is MySalesEvent.AcceptOrder -> acceptOrder(event.orderId)
        }
    }

    private fun getShopOrders() {
        viewModelScope.launch {
            state.user?.shop?.id?.let { shopId ->
                shopRepository.getShopOrders(shopId, true).collect { result ->
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

    private fun acceptOrder(orderId: Int) {
        viewModelScope.launch {
            orderRepository.updateOrder(
                orderId, UpdateOrderDto(
                    orderStatus = null,
                    accepted = true,
                    courierId = null
                )
            ).collect { result ->
                when (result) {
                    is Resource.Success -> updateOrderStatus(orderId)
                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun updateOrderStatus(orderId: Int) {
        viewModelScope.launch {
            orderRepository.updateOrder(
                orderId, UpdateOrderDto(
                    orderStatus = OrderStatus.Packaged.toString(),
                    accepted = null,
                    courierId = null
                )
            ).collect { result ->
                when (result) {
                    is Resource.Success -> getShopOrders()
                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun updateOrderCourier(orderId: Int, courierId: Int) {
        viewModelScope.launch {
            orderRepository.updateOrder(
                orderId, UpdateOrderDto(
                    orderStatus = null,
                    accepted = null,
                    courierId = courierId
                )
            ).collect { result ->
                when (result) {
                    is Resource.Success -> getShopOrders()
                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun getCouriers() {
        viewModelScope.launch {
            state.user?.shop?.id?.let {
                courierRepository.getCouriers().collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(couriers = result.data.data)
                            }
                        }

                        is Resource.Error -> handleError()
                        is Resource.Loading -> handleLoading(result.isLoading)
                    }
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
                        getShopOrders()
                        getCouriers()
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