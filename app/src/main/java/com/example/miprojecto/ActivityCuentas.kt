package com.example.miprojecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.miprojecto.SaveDatos.Companion.prefs
import com.example.miprojecto.databinding.ActivityCuentasBinding


class ActivityCuentas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCuentasBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_cuentas)

    }

    /*  fun irCalendar(view: View) {
          val intent = Intent(this, ActivityFecha::class.java).apply {
              val message = "mostrando"
              putExtra(ACTIVITY_PRINCIPAL, message)
          }
          startActivity(intent)
      }*/

    fun onClick(view: View) {
        when (view.id) {
           // R.id.btn_editar -> startActivity(Intent(this, ActivityFecha::class.java))
            R.id.btn_agregar -> startActivity(Intent(this, ActivityFormulario::class.java))
        }
    }
}