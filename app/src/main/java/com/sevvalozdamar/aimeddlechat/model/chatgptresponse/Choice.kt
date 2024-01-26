package com.sevvalozdamar.aimeddlechat.model.chatgptresponse

data class Choice(
    val finish_reason: String,
    val index: Int,
    val logprobs: Any,
    val message: Message
)