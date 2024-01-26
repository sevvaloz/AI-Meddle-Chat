package com.sevvalozdamar.aimeddlechat.model.chatgptresponse

data class BaseResponse(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage
)