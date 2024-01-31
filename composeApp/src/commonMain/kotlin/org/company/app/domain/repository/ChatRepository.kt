package org.company.app.domain.repository

import org.company.app.domain.models.ChatRoom

interface ChatRepository {

    suspend fun createChatRoom(name: String): ChatRoom

    suspend fun fetchChatRooms(): List<ChatRoom>

    suspend fun joinChatRoom(roomId: Long): ChatRoom

    suspend fun leaveChatRoom(roomId: Long)

}