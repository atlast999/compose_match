package org.company.app.presentation.chat.room

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.company.app.domain.models.ChatRoom
import org.company.app.domain.repository.ChatRepository

class ChatRoomScreenModel(
    private val chatRepository: ChatRepository,
) : StateScreenModel<ChatRoomScreenModel.State>(
    initialState = State()
) {

    init {
        fetchRooms()
    }


    fun fetchRooms() = screenModelScope.launch {
        mutableState.update {
            it.copy(isLoading = true)
        }
        val rooms = chatRepository.fetchChatRooms()
        mutableState.update {
            it.copy(
                isLoading = false,
                rooms = rooms,
            )
        }
    }

    fun createRoom(name: String) = screenModelScope.launch {
        mutableState.update {
            it.copy(isLoading = true)
        }
        val createdRoom = chatRepository.createChatRoom(name = name)
        mutableState.update {
            it.copy(
                isLoading = false,
                joinedRoom = createdRoom,
            )
        }
    }

    fun joinChatRoom(room: ChatRoom) = screenModelScope.launch {
        mutableState.update {
            it.copy(isLoading = true)
        }
        val joinedRoom = chatRepository.joinChatRoom(roomId = room.id)
        mutableState.update {
            it.copy(
                isLoading = false,
                joinedRoom = joinedRoom,
            )
        }
    }

    data class State(
        val isLoading: Boolean = true,
        val rooms: List<ChatRoom> = emptyList(),
        val joinedRoom: ChatRoom? = null,
    )
}