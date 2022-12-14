package com.example.miprojecto.corrutina

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class App:Application() {

   override fun onCreate(){
        super.onCreate()
       GlobalScope.launch(Dispatchers.Main) {
           val results = withContext(Dispatchers.Main) { CorrutinaProvider.circleCorrutina() }
           println(results)
       }
    }
}