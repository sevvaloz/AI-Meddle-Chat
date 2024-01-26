package com.sevvalozdamar.aimeddlechat.ui.chatdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevvalozdamar.aimeddlechat.repository.SentimentRepository
import com.sevvalozdamar.aimeddlechat.network.SentimentAnalysisService
import kotlinx.coroutines.launch

class SentimentViewModel() : ViewModel() {

    private var _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    private var repository: SentimentRepository? = null

    init {
        repository = SentimentRepository(SentimentAnalysisService)
    }

    fun getSentimentResult(sentence: String) =  viewModelScope.launch {
        _result.value = repository?.getSentimentResult(sentence)
    }
}