package com.triforce.malacprodavac.presentation.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profile: Profile,
    private val repository: Profile
) : ViewModel() {
    var state by mutableStateOf(ProfileState())

    init {
        me()
        getToken()
    }

    fun onEvent(event: ProfileEvent) {
        viewModelScope.launch {
            when (event) {
                is ProfileEvent.Logout -> {
                    logout()
                }
                is ProfileEvent.onAddMediaButtonPress -> {
                    state.newImage = true
                }
            }
        }
    }


    fun isLoggedIn(): Boolean {
        return state.isLoggedIn
    }

    private fun getToken() {
        profile.getToken().let {
            Log.d("TOKEN", it.toString())
            state = state.copy(token = it)
        }
    }

    private fun me() {
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
                            currentUser = result.data
                        )
                    }
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
                }
            }
        }
    }

    fun pickMedia(mediaUris: List<Uri>) {
        viewModelScope.launch {
            state = state.copy(mediaUris = mediaUris)
            Log.d("PICKED_MEDIA", mediaUris.first().path.toString())
        }
    }
}