package com.example.miprojecto.corrutina

import java.lang.Thread.sleep
import kotlin.contracts.Returns

object CorrutinaProvider {
    fun circleCorrutina():Int{
        sleep(1000L)
        return 100
    }
}