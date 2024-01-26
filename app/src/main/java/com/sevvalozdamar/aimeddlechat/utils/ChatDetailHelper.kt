package com.sevvalozdamar.aimeddlechat.utils

import android.util.Log
import com.sevvalozdamar.aimeddlechat.model.base.SentimentScore
import com.sevvalozdamar.aimeddlechat.model.base.SuggestionMessage
import org.json.JSONArray

object ChatDetailHelper {

    private fun findMaxScoreEmotion(sentimentScore: SentimentScore): String {
        val scores = listOf(
            sentimentScore.fearScore,
            sentimentScore.angerScore,
            sentimentScore.happyScore,
            sentimentScore.surpriseScore,
            sentimentScore.sadScore
        )

        val maxScore = scores.maxOrNull()

        return if (maxScore != null) {
            val maxScoreIndex = scores.indexOf(maxScore)
            when (maxScoreIndex) {
                0 -> "Fear"
                1 -> "Anger"
                2 -> "Happy"
                3 -> "Surprise"
                4 -> "Sad"
                else -> {"else sentiment"}
            }
        } else {
            "No emotion found"
        }
    }


    fun findMaxScore(sentimentScore: SentimentScore): Double {
        return listOf(sentimentScore.fearScore, sentimentScore.angerScore, sentimentScore.happyScore, sentimentScore.surpriseScore, sentimentScore.sadScore).maxOrNull()
            ?: Double.MIN_VALUE
    }

    private fun formatDouble(number: Double?): Double {
        return "%.2f".format(number).toDouble()
    }

    fun jsonToSentiment(jsonString: String): String {
        try {
            val jsonArray = JSONArray("[$jsonString]")

            var fearScore: Double = 0.0
            var angerScore: Double = 0.0
            var happyScore: Double = 0.0
            var surpriseScore: Double = 0.0
            var sadScore: Double = 0.0

            for (i in 0 until jsonArray.length()) {
                val data = jsonArray.getJSONObject(i)
                when (data.getString("label")) {
                    "LABEL_0" -> {
                        fearScore = formatDouble(data.getDouble("score"))
                        Log.d("BAK", "Fear Sentiment Result: $fearScore")
                    }
                    "LABEL_1" -> {
                        angerScore = formatDouble(data.getDouble("score"))
                        Log.d("BAK", "Anger Sentiment Result: $angerScore")
                    }
                    "LABEL_2" -> {
                        happyScore = formatDouble(data.getDouble("score"))
                        Log.d("BAK", "Happy Sentiment Result: $happyScore")
                    }
                    "LABEL_3" -> {
                        surpriseScore = formatDouble(data.getDouble("score"))
                        Log.d("BAK", "Surprise Sentiment Result: $surpriseScore")
                    }
                    "LABEL_4" -> {
                        sadScore = formatDouble(data.getDouble("score"))
                        Log.d("BAK", "Sad Sentiment Result: $sadScore")
                    }
                }
            }

            return findMaxScoreEmotion(
                SentimentScore(
                    fearScore = fearScore,
                    angerScore = angerScore,
                    happyScore = happyScore,
                    surpriseScore = surpriseScore,
                    sadScore = sadScore
                )
            )

        } catch (e: Exception) {
            e.printStackTrace()
            return "${e.message}"
        }
    }

    fun removeOuterBrackets(text: String): String {
        val startIndex = if (text.startsWith("[[")) 2 else 0
        val endIndex = if (text.endsWith("]]")) text.length - 2 else text.length
        return text.substring(startIndex, endIndex)
    }

    fun removeOuterQuote(text: String): String {
        val startIndex = if (text.startsWith("\"")) 1 else 0
        val endIndex = if (text.endsWith("\"")) text.length - 1 else text.length
        return text.substring(startIndex, endIndex)
    }

    fun parseResponseToSuggestionMessages(response: String): MutableList<SuggestionMessage> {
        val messages = response.split("\n")
        val suggestionMessages = mutableListOf<String>()

        for (message in messages) {
            val index = message.indexOf('.')
            if (index != -1 && index < message.length - 1) {
                val item = message.substring(index + 2).trim()
                suggestionMessages.add(item)
            }
        }

        val suggestionMessageMutableList = mutableListOf<SuggestionMessage>()
        suggestionMessages.forEach {
            suggestionMessageMutableList.add(SuggestionMessage(it))
        }

        return  suggestionMessageMutableList
    }

    fun createPrompt(messageFromOther: String, messageFromMe: String? = null): String {
        return if(messageFromMe != null){
            "Arkadaşım ve ben çevrimiçi bir mesajlaşma uygulaması üzerinden mesajlaşıyoruz. Ben arkadaşıma \"$messageFromMe\" mesajını yazdım. Arkadaşım da benim bu mesajıma karşılık olarak \"$messageFromOther\" mesajını yazdı. Arkadaşımın yazdığı bu son mesaja benim verilebileceğim en uygun yanıtları 1. 2. 3. şeklinde madde madde yaz. Önerdiğin mesaj sayısı, kesinlikle 3 tane olmalı. Bu öneri mesajlarının her biri, 50 karakteri geçmemeli ve arkadaşıma yazacağım için samimi bir dille olmalı."
        } else {
            "Arkadaşım ve ben çevrimiçi bir mesajlaşma uygulaması üzerinden mesajlaşıyoruz. Arkadaşım bana \"$messageFromOther\" mesajını yazdı. Arkadaşımın yazdığı bu mesaja benim verilebileceğim en uygun yanıtları 1. 2. 3. şeklinde madde madde yaz. Önerdiğin mesaj sayısı, kesinlikle 3 tane olmalı. Bu öneri mesajlarının her biri, 50 karakteri geçmemeli ve arkadaşıma yazacağım için samimi bir dille olmalı."
        }
    }

}