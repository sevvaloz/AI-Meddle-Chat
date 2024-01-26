package com.sevvalozdamar.aimeddlechat.network

import com.sevvalozdamar.aimeddlechat.utils.Constants.SA_API_TOKEN
import com.sevvalozdamar.aimeddlechat.utils.Constants.SA_API_URL
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody

object SentimentAnalysisService {
    fun getSentimentAnalysisResult(sentence: String): String {
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        val requestBody = RequestBody.create(mediaType, sentence)
        val request = Request.Builder()
            .url(SA_API_URL)
            .post(requestBody)
            .addHeader("Authorization", "Bearer $SA_API_TOKEN")
            .build()
        return client.newCall(request).execute().body()?.string() ?: "Sentiment Analysis API Error"
    }
}