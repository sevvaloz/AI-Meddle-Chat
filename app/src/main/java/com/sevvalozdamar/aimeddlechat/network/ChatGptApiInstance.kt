package com.sevvalozdamar.aimeddlechat.network

import com.sevvalozdamar.aimeddlechat.utils.Constants.GPT_API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object ChatGptApiInstance {
    val service: ChatGptService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(GPT_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ChatGptService::class.java)
    }
}