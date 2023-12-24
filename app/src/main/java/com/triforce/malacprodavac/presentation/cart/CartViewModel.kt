package com.triforce.malacprodavac.presentation.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.data.remote.orders.dto.CreateOrderDto
import com.triforce.malacprodavac.data.remote.orders.dto.CreateSchedulePickupDto
import com.triforce.malacprodavac.data.repository.cart.CartRepository
import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.domain.model.shops.Shop
import com.triforce.malacprodavac.domain.repository.OrderRepository
import com.triforce.malacprodavac.domain.repository.ScheduledPickupRepository
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import com.triforce.malacprodavac.presentation.cart.components.CartItem
import com.triforce.malacprodavac.util.enum.DeliveryMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(

    private val profile: Profile,
    private val repositoryOrder: OrderRepository,
    private val repositorySchedule: ScheduledPickupRepository

) : ViewModel() {

    var cartState by mutableStateOf(CartState())

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    init {
        me()
    }

    private fun fetchCartItems() {
        _cartItems.value = CartRepository.getCartItems()
        recalculateTotalPrice()
    }

    fun addToCart(product: Product, shop: Shop?) {
        val existingItem = _cartItems.value.find { it.product.id == product.id }

        if (existingItem != null) {
            existingItem.quantity.value++
        } else {
            val newItem = CartItem(product, shop)
            CartRepository.addItemToCart(newItem)
        }

        fetchCartItems()
    }

    fun removeFromCart(item: CartItem) {
        CartRepository.removeItemFromCart(item)
        fetchCartItems()
    }

    fun clearCart() {
        CartRepository.clearCart()
        fetchCartItems()
    }

    private fun recalculateTotalPrice() {
        val totalPrice = _cartItems.value.sumOf { it.price.value * it.quantity.value }
        cartState = cartState.copy(totalPrice = totalPrice)
    }

    fun onEvent(event: CartEvent) {
        when (event) {
            CartEvent.quantityChange -> recalculateTotalPrice()
            CartEvent.makeOrder ->
                for (cartItem in _cartItems.value)
                    if (CartRepository.getShipping() == DeliveryMethod.ByCourier)
                        makeCourierOrder(cartItem)
                    else if (CartRepository.getShipping() == DeliveryMethod.SelfPickup)
                        makeScheduleOrder(cartItem)
        }
    }

    private fun makeCourierOrder(
        cartItem: CartItem
    ) {
        viewModelScope.launch {
            repositoryOrder.insertOrder(
                createOrder = CreateOrderDto(
                    deliveryMethod = CartRepository.getShipping(),
                    paymentMethod = CartRepository.getPayment(),
                    productId = cartItem.product.id,
                    quantity = cartItem.quantity.value
                )
            ).collect { result ->
                when (result) {
                    is Resource.Success ->
                        result.data?.let {
                            cartState = cartState.copy(isSuccessful = true)
                        }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun makeScheduleOrder(
        cartItem: CartItem
    ) {
        viewModelScope.launch {
            repositoryOrder.insertOrder(
                createOrder = CreateOrderDto(
                    deliveryMethod = CartRepository.getShipping(),
                    paymentMethod = CartRepository.getPayment(),
                    productId = cartItem.product.id,
                    quantity = cartItem.quantity.value
                )
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { order ->
                            scheduleOrder(order)
                        }
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun scheduleOrder(
        order: Order
    ) {
        viewModelScope.launch {
            repositorySchedule.insertScheduledPickup(
                id = order.id,
                createSchedulePickup = CreateSchedulePickupDto(
                    date = CartRepository.getScheduleDate(),
                    timeOfDay = CartRepository.getScheduleTime()
                )
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            cartState = cartState.copy(isSuccessful = true)
                        }
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun me() {
        viewModelScope.launch {
            profile.getMe().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        cartState = cartState.copy(
                            user = result.data,
                            profileImageUrl = "http://softeng.pmf.kg.ac.rs:10010/users/${result.data?.profilePicture?.userId}/medias/${result.data?.profilePicture?.id}",
                            profileImageKey = result.data?.profilePicture?.key
                        )

                        getToken()
                        fetchCartItems()
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun getToken() {
        profile.getToken().let {
            cartState = cartState.copy(token = it)
        }
    }

    private fun handleError() {}

    private fun handleLoading(isLoading: Boolean) {
        cartState = cartState.copy(isLoading = isLoading)
    }
}