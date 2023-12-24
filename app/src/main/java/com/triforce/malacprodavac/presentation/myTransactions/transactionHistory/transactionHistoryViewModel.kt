package com.triforce.malacprodavac.presentation.myTransactions.transactionHistory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.repository.CustomerRepository
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(

    private val profile: Profile,
    private val customerRepository: CustomerRepository

) : ViewModel() {

    var state by mutableStateOf(TransactionHistoryState())

    init {
        me()
    }

    private fun getUserOrders() {
        viewModelScope.launch {
            state.user?.customer?.id?.let { customerId ->
                customerRepository.getUserOrders(customerId, true).collect { result ->
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

    fun me() {
        viewModelScope.launch {
            profile.getMe().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            user = result.data,
                        )

                        getToken()
                        getUserOrders()
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