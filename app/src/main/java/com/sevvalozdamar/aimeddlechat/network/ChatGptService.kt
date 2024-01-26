package com.sevvalozdamar.aimeddlechat.network

import com.sevvalozdamar.aimeddlechat.model.chatgptrequest.BaseRequest
import com.sevvalozdamar.aimeddlechat.model.chatgptresponse.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGptService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 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"
    )
    @POST("v1/chat/completions")
    fun getSuggestionMessages(@Body request: BaseRequest): Call<BaseResponse>
}