package org.company.app.presentation.chat

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.company.app.network.SpaceXApi

class ChatScreenModel (
    private val spaceXApi: SpaceXApi,
) : StateScreenModel<ChatScreenModel.State>(
    initialState = State()
) {

    private lateinit var sendChannel1: SendChannel<Frame>
    private lateinit var sendChannel2: SendChannel<Frame>

    init {
        screenModelScope.launch {
            launch {
                spaceXApi.getWebSocketSession().apply {
                    sendChannel1 = outgoing
                    incoming.receiveAsFlow().collect { frame ->
                        (frame as? Frame.Text)?.readText()?.let { message ->
                            mutableState.update {
                                it.copy(
                                    content1 = it.content1 + "\n" + message,
                                )
                            }
                        }
                    }
                }
            }

            launch {
                spaceXApi.getWebSocketSession().apply {
                    sendChannel2 = outgoing
                    incoming.receiveAsFlow().collect { frame ->
                        (frame as? Frame.Text)?.readText()?.let { message ->
                            mutableState.update {
                                it.copy(
                                    content2 = it.content2 + "\n" + message,
                                )
                            }
                        }
                    }
                }
            }
            mutableState.update {
                it.copy(isLoading = false)
            }
        }
    }

    fun send1(message: String) = screenModelScope.launch {
        sendChannel1.send(Frame.Text(text = message))
    }

    fun send2(message: String) = screenModelScope.launch {
        sendChannel2.send(Frame.Text(text = message))
    }

    data class State(
        val isLoading: Boolean = true,
        val content1: String = "",
        val content2: String = "",
    )
}