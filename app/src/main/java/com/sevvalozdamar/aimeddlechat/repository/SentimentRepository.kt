package com.sevvalozdamar.aimeddlechat.repository

import android.util.Log
import com.sevvalozdamar.aimeddlechat.network.SentimentAnalysisService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SentimentRepository(
    private val sentimentAnalysisAPI: SentimentAnalysisService
) {

    suspend fun getSentimentResult(sentence: String): String = withContext(Dispatchers.IO) {
        try {
            val response = sentimentAnalysisAPI.getSentimentAnalysisResult(sentence)
            return@withContext response
        } catch (e: Exception) {
            val response = sentimentAnalysisAPI.getSentimentAnalysisResult(sentence)
            Log.d("BAK", "Error: ${e.message}")
            return@withContext response
        }
    }

}