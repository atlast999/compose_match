package org.company.app.presentation.chat

import cafe.adriel.voyager.core.model.StateScreenModel
import org.company.app.domain.models.ChatMessage
import org.company.app.domain.repository.ChatRepository

class ChatScreenModel(
    private val chatRepository: ChatRepository,
) : StateScreenModel<ChatScreenModel.State>(
    initialState = State()
) {
    data class State(
        val isLoading: Boolean = true,
        val messages: List<ChatMessage> = emptyList(),
    )
}