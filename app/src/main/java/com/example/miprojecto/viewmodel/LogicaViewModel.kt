package com.example.miprojecto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.miprojecto.model.QuotModel
import com.example.miprojecto.model.QuotProvider

class LogicaViewModel:ViewModel() {

    val quotModel = MutableLiveData<QuotModel>()

    fun quotRandom(){
        val currentQuot:QuotModel = QuotProvider.random()
        quotModel.postValue(currentQuot)
    }
}