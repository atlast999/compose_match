package org.company.app.domain.models

data class ChatRoom(
    val id: Long,
    val name: String,
    val owner: ChatMember,
    val members: List<ChatMember>,
)

data class ChatMember(
    val id: Long,
    val name: String,
)

data class ChatMessage(
    val owner: ChatMember,
    val content: String,
)