package com.example.miprojecto.corrutina

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel:ViewModel() {

    fun SegCorrutina(){
        viewModelScope.launch{
            val result = withContext(Dispatchers.Main){CorrutinaProvider.circleCorrutina()}
            println(result)
        }
    }
}