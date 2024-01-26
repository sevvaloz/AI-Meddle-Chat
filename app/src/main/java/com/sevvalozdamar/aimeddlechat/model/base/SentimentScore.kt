package com.sevvalozdamar.aimeddlechat.model.base

data class SentimentScore(
    val fearScore: Double,
    val angerScore: Double,
    val happyScore: Double,
    val surpriseScore: Double,
    val sadScore: Double
)