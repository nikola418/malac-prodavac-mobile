package com.triforce.malacprodavac.presentation.profile.profilePrivate

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.data.mappers.toUpdateCourier
import com.triforce.malacprodavac.data.mappers.toUpdateCustomer
import com.triforce.malacprodavac.data.mappers.toUpdateShop
import com.triforce.malacprodavac.data.mappers.users.toUpdateUser
import com.triforce.malacprodavac.data.services.SessionManager
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import com.triforce.malacprodavac.domain.util.compressedFileFromUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfilePrivateViewModel @Inject constructor(
    private val profile: Profile,
    private val sessionManager: SessionManager
) : ViewModel() {
    var state by mutableStateOf(ProfilePrivateState())

    init {
        me()
        getToken()
    }

    fun onEvent(event: ProfilePrivateEvent) {
        when (event) {
            is ProfilePrivateEvent.Logout -> {
                logout()
            }

            is ProfilePrivateEvent.ChangeProfilePicture -> {
                setProfilePicture(event.context, event.uri)
            }

            ProfilePrivateEvent.Refresh -> {
                me()
            }

            ProfilePrivateEvent.Edit -> {
                state = state.copy(isEditing = !state.isEditing)
            }

            is ProfilePrivateEvent.AddressChanged -> {
                state = state.copy(updateUser = state.updateUser?.copy(address = event.address))
            }

            is ProfilePrivateEvent.FirstNameChanged -> {
                state =
                    state.copy(updateUser = state.updateUser?.copy(firstName = event.firstName))
            }

            is ProfilePrivateEvent.LastNameChanged -> {
                state = state.copy(updateUser = state.updateUser?.copy(lastName = event.lastName))
            }

            is ProfilePrivateEvent.PhoneNumberChanged -> {
                state =
                    state.copy(updateUser = state.updateUser?.copy(phoneNumber = event.phoneNumber))
            }

            is ProfilePrivateEvent.BusinessNameChanged -> {
                state = state.copy(
                    updateShop = state.updateShop?.copy(
                        businessName = event.businessName
                    )
                )
            }

            is ProfilePrivateEvent.AvailableAtChanged -> {
                state = state.copy(
                    updateShop = state.updateShop?.copy(
                        availableAt = event.availableAt
                    )
                )
            }


            is ProfilePrivateEvent.OpenFromChanged -> {
                state = state.copy(
                    updateShop = state.updateShop?.copy(
                        openFrom = event.openFrom
                    )
                )
            }

            is ProfilePrivateEvent.OpenTillChanged -> {
                state = state.copy(
                    updateShop = state.updateShop?.copy(
                        openTill = event.openTill
                    )
                )
            }

            is ProfilePrivateEvent.OpenFromDaysChanged -> {
                state = state.copy(
                    updateShop = state.updateShop?.copy(
                        openFromDays = event.openFromDays
                    )
                )
            }

            is ProfilePrivateEvent.OpenTillDaysChanged -> {
                state = state.copy(
                    updateShop = state.updateShop?.copy(
                        openTillDays = event.openTillDays
                    )
                )
            }

            ProfilePrivateEvent.SubmitEdit -> {
                updateCustomer()
                if (state.currentUser?.roles?.contains("Courier") == true)
                    updateCourier()
                if (state.currentUser?.roles?.contains("Shop") == true)
                    updateShop()
            }

        }
    }

    fun isLoggedIn(): Boolean {
        return sessionManager.isAuthenticated()
    }

    private fun getToken() {
        profile.getToken().let {
            state = state.copy(token = it)
        }
    }

    private fun updateCustomer() {
        Log.d("DSF", state.updateUser.toString())
        viewModelScope.launch {
            profile.updateCustomer(
                state.currentUser!!.customer!!.id,
                state.updateCustomer!!.copy(user = state.updateUser)
            ).collect {
                when (it) {
                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = it.isLoading)
                    }

                    is Resource.Success -> {
                        me()
                    }
                }
            }
        }
    }

    private fun updateCourier() {
        viewModelScope.launch {
            profile.updateCourier(
                state.currentUser!!.courier!!.id,
                state.updateCourier!!
            ).collect {
                when (it) {
                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = it.isLoading)
                    }

                    is Resource.Success -> {
                        me()
                    }
                }
            }
        }
    }

    private fun updateShop() {
        viewModelScope.launch {
            profile.updateShop(
                state.currentUser!!.shop!!.id,
                state.updateShop!!
            ).collect {
                when (it) {
                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = it.isLoading)
                    }

                    is Resource.Success -> {
                        me()
                    }
                }
            }
        }
    }

    fun me() {
        viewModelScope.launch {
            profile.getMe().collect { result ->
                when (result) {
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            currentUser = result.data,
                            profileImageUrl = "http://softeng.pmf.kg.ac.rs:10010/users/${result.data?.profilePicture?.userId}/medias/${result.data?.profilePicture?.id}",
                            profileImageKey = result.data?.profilePicture?.key,
                            updateUser = result.data?.toUpdateUser(),
                            updateCustomer = result.data?.customer?.toUpdateCustomer(),
                            updateCourier = result.data?.courier?.toUpdateCourier(),
                            updateShop = result.data?.shop?.toUpdateShop()

                        )

                    }

                    else -> {}
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            profile.logout().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(isLoggedIn = false)
                    }

                    is Resource.Error -> TODO()
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setProfilePicture(context: Context, uri: Uri) {
        viewModelScope.launch {
            val file = compressedFileFromUri(context, uri)
            profile.setProfilePicture(state.currentUser!!.id, file)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {

                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }

                        is Resource.Success -> {
                            state =
                                state.copy(
                                    profileImageUrl = "http://softeng.pmf.kg.ac.rs:10010/users/${result.data?.userId}/medias/${result.data?.id}",
                                    profileImageKey = result.data?.key
                                )
                        }
                    }
                }
        }
    }
}