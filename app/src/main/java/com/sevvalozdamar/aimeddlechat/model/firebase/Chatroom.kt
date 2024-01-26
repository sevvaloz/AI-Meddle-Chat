package com.sevvalozdamar.aimeddlechat.model.firebase

import com.google.firebase.Timestamp

data class Chatroom(
    var chatroomId: String? = null,
    var userIds: List<String>? = null,
    var lastMessageTimestamp: Timestamp? = null,
    var lastMessageSenderId: String? = null,
    var lastMessage: String? = null
)