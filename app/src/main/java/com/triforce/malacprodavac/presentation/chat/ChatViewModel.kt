package com.triforce.malacprodavac.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import com.triforce.malacprodavac.presentation.chat.components.SocketHandler
import com.triforce.malacprodavac.presentation.home.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val profile: Profile
) : ViewModel() {

    var state by mutableStateOf(HomeScreenState())

    var mSocket: Socket? = null

    init {
        state.copy(isLoading = true)

        initSocket()


        me()
        getToken()
    }

    fun initSocket() {
        SocketHandler.setSocket()
        mSocket = SocketHandler.getSocket()

        mSocket!!.connect()

        mSocket!!.emit("init_chat")
    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            ChatEvent.Refresh -> {
                me()
            }

            is ChatEvent.SendMessage -> {
                mSocket!!.emit("send_msg", event.msg)
            }

            ChatEvent.RecieveMessage -> {
                mSocket!!.on("recieve_msg") { args ->
                    if (args[0] != null) {
                        val msg = args[0] as String

                        // msgBox.text = msg
                    }
                }
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return profile.isAuthenticated.invoke()
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
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            currentUser = result.data,
                            profileImageUrl = "http://softeng.pmf.kg.ac.rs:10010/users/${result.data?.profilePicture?.userId}/medias/${result.data?.profilePicture?.id}",
                            profileImageKey = result.data?.profilePicture?.key
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}