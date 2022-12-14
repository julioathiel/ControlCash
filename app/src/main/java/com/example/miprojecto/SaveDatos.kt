package com.example.miprojecto

import android.app.Application

import com.example.miprojecto.Prefs

class SaveDatos:Application() {

    companion object{
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}