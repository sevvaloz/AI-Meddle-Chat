package com.sevvalozdamar.aimeddlechat.model.firebase

import com.google.firebase.Timestamp

data class Message(
    val messageId: String? = null,
    val messageText: String? = null,
    val senderId: String? = null,
    val timestamp: Timestamp? = null,
    var sentiment: String? = null
)
