package org.company.app.data.repository

import kotlinx.coroutines.runBlocking
import org.company.app.domain.models.ChatMember
import org.company.app.domain.models.ChatRoom
import org.company.app.domain.repository.ChatRepository

class ChatRepositoryImpl : ChatRepository {

    override suspend fun createChatRoom(name: String): ChatRoom {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChatRooms(): List<ChatRoom> {
        TODO("Not yet implemented")
    }

    override suspend fun joinChatRoom(roomId: Long): ChatRoom {
        TODO("Not yet implemented")
    }

    override suspend fun leaveChatRoom(roomId: Long) {
        TODO("Not yet implemented")
    }
}

class FakeChatRepositoryImpl : ChatRepository {

    companion object {
        private val rooms = mutableListOf<ChatRoom>()
    }

    init {
        runBlocking {
            createChatRoom(name = "Fake new room")
        }
    }

    override suspend fun createChatRoom(name: String): ChatRoom {
        val owner = ChatMember(
            id = -1,
            name = "User fake",
        )
        return ChatRoom(
            id = rooms.lastOrNull()?.id?.inc() ?: 0,
            name = name,
            owner = owner,
            members = listOf(owner),
        ).also {
            rooms.add(it)
        }
    }

    override suspend fun fetchChatRooms(): List<ChatRoom> {
        return rooms.toList()
    }

    override suspend fun joinChatRoom(roomId: Long): ChatRoom {
        return rooms.first { it.id == roomId }
    }

    override suspend fun leaveChatRoom(roomId: Long) {

    }

}