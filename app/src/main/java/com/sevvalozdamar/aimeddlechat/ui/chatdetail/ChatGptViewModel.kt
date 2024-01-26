package com.sevvalozdamar.aimeddlechat.ui.chatdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevvalozdamar.aimeddlechat.model.chatgptrequest.BaseRequest
import com.sevvalozdamar.aimeddlechat.model.chatgptresponse.BaseResponse
import com.sevvalozdamar.aimeddlechat.network.ChatGptApiInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatGptViewModel(): ViewModel() {

    private var _result = MutableLiveData<BaseResponse>()
    val result: LiveData<BaseResponse> get() = _result


    fun getSuggestionMessages(request: BaseRequest) =  viewModelScope.launch {
        ChatGptApiInstance.service.getSuggestionMessages(request).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    _result.value = response.body()
                } else {
                    Log.d("BAK2", "getSuggestionMessages Empty Body: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("BAK2", "getSuggestionMessages Error: ${t.message}")
            }
        })

    }

}