package com.sevvalozdamar.aimeddlechat.model.base

import com.google.firebase.Timestamp

data class BaseMessage(
    val messageId: String? = null,
    val messageText: String? = null,
    val senderId: String? = null,
    val timestamp: Timestamp? = null,
    val sentimentScore: Double
)
