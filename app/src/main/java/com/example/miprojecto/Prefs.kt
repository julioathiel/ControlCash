package com.example.miprojecto

import android.content.Context

class Prefs(val context: Context) {

    val shared_name = "julio"
    val SHARED_FECHA_KEY = "fecha"
    val SHARED_VIP = "vip"
    val SHARED_DIA = "dia"
    val SHARED_DINERO = "dinero"
    val SHARED_ACUMULADOR = "acumulador"
    val SHARED_COUNTADOR_CIRCLE = "contador"
    val SHARED_COUNT_DIA_ACTUAL_CIRCLEBAR = "aumentDia"

    val storage = context.getSharedPreferences(shared_name, 0)

    fun saveFecha(fecha: String) {
        storage.edit().putString(SHARED_FECHA_KEY, fecha).apply()
    }

    fun getFecha(): String {
        return storage.getString(SHARED_FECHA_KEY, "")!!
    }

    fun saveSuma(dinero: String) {
        storage.edit().putString(SHARED_DINERO, dinero).apply()
    }

    fun getSuma(): String {
        return storage.getString(SHARED_DINERO, "")!!
    }


    fun saveDia(dia: String) {
        storage.edit().putString(SHARED_DIA, dia).apply()
    }

    fun getDia(): String {
        return storage.getString(SHARED_DIA, "")!!
    }


    fun saveVip(vip: Boolean) {
        storage.edit().putBoolean(SHARED_VIP, vip).apply()
    }

    fun getVip(): Boolean {
        return storage.getBoolean(SHARED_VIP, false)
    }

    fun saveAcumuladorString(acumulador: String) {
        storage.edit().putString(SHARED_ACUMULADOR, acumulador).apply()
    }

    fun getAcumuladorString(): String {
        return storage.getString(SHARED_ACUMULADOR, "")!!
    }

    fun saveCountCircleString(acumulador: String) {
        storage.edit().putString(SHARED_COUNTADOR_CIRCLE, acumulador).apply()
    }

    fun getCountCircleString(): String {
        return storage.getString(SHARED_COUNTADOR_CIRCLE, "")!!
    }

    fun saveStringDiaActualCircleBar(aumentDia: Int) {
        storage.edit().putInt(SHARED_COUNT_DIA_ACTUAL_CIRCLEBAR, aumentDia).apply()
    }

    fun getStringDiaActualCircleBar(diaActual: Int): Int {
        return storage.getInt(SHARED_COUNT_DIA_ACTUAL_CIRCLEBAR, 0)
    }


}