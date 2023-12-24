package com.triforce.malacprodavac.presentation.profile.profilePublic


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.repository.ShopRepository
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePublicViewModel @Inject constructor(

    private val profile: Profile,
    private val repositoryShop: ShopRepository,

    savedStateHandle: SavedStateHandle

) : ViewModel() {
    var state by mutableStateOf(ProfilePublicState())

    init {
        state = state.copy(isLoading = true)
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != -1) {
                savedStateHandle.get<Int>("role")?.let { role ->
                    if (role == 1) {
                        state = state.copy(role = 1)
                        getShop(id)
                    }
                }
            } else {
                me()
                getToken()
            }
        }
    }

    private fun getToken() {
        profile.getToken().let {
            state = state.copy(token = it)
        }
    }

    private fun me() {
        viewModelScope.launch {
            profile.getMe().collect { result ->
                when (result) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        state = state.copy(user = result.data)
                        Log.d("JEBEM_PRODUCTS", state.user?.shop?.products.toString())
                        state.user?.shop?.id?.let { getShop(it) }
                    }
                }
            }
        }
    }

    private fun getShop(shopId: Int) {
        viewModelScope.launch {
            repositoryShop.getShop(fetchFromRemote = true, id = shopId)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (result.data != null)
                                state = state.copy(


                                    shop = result.data,
                                    isLoading = false,
                                    profileImageKey = result.data.user?.profilePicture?.key,
                                    profileImageUrl = "http://softeng.pmf.kg.ac.rs:10010/users/${result.data.user?.profilePicture?.userId}/medias/${result.data.user?.profilePicture?.id}"
                                )
                            Log.d(
                                "PROVILE_IMAGE_KEY",
                                result.data?.user?.profilePicture?.key.toString()
                            )
                            result.data?.user?.toString()?.let { Log.d("PROVILE_USER", it) }

                        }

                        is Resource.Error -> {
                            Unit
                        }

                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }
        }
    }
}