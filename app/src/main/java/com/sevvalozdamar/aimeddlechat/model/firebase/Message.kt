package com.sevvalozdamar.aimeddlechat.model.firebase

import com.google.firebase.Timestamp
import com.sevvalozdamar.aimeddlechat.model.base.Sentiment

data class Message(
    val messageId: String? = null,
    val messageText: String? = null,
    val senderId: String? = null,
    val timestamp: Timestamp? = null,
    var sentiment: Sentiment? = null
)
