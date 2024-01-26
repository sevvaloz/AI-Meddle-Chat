package com.sevvalozdamar.aimeddlechat.model.chatgptrequest

data class BaseRequest(
    val messages: List<Message>,
    val model: String,
    val temperature: Double
)