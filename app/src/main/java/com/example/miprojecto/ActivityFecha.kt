package com.example.miprojecto

import android.content.Intent
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miprojecto.SaveDatos.Companion.prefs
import com.example.miprojecto.databinding.ActivityFechaBinding

import java.time.LocalDate

import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds


class ActivityFecha : AppCompatActivity() {

    private var date: String = ""
    private lateinit var fechaOriginal: String
    private lateinit var binding: ActivityFechaBinding
    private val fechaActual: LocalDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFechaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fechaOriginal = ""




        binding.datePicker.setOnClickListener { showDatePickerDialog() }


    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datepicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.datePicker.setText(
            "${String.format("%02d", day)}/${
                String.format(
                    "%02d",
                    month.plus(1)
                )
            }/${year}"
        )
        fechaOriginal =
            "$year-${String.format("%02d", month.plus(1))}-${String.format("%02d", day)}"
         date = "${String.format("%02d", day)}/${String.format("%02d", month.plus(1))}/${year}"
        binding.fechaElegida.text = "Proximo descuento $date"
        val dia = dia(fechaActual, fechaOriginal)
        binding.contadordias.text = "faltan $dia dias"

        binding.btnFechaGuardada.setOnClickListener {
            if (date.isEmpty()) {
                Toast.makeText(this, "Selecciona fecha", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Fecha  $date guardada", Toast.LENGTH_SHORT).show()
                prefs.saveFecha(date)
                prefs.saveDia(fechaOriginal)
                startActivity(Intent(this, ActivityCuentas::class.java))
            }
        }
    }

    private fun dia(fechaActual: LocalDate?, fDespues: String?): Long {
        val fLocalDate = LocalDate.parse(fDespues)
        var dia: Long = ChronoUnit.DAYS.between(fechaActual, fLocalDate)
        dia += 1
        return dia
    }


}

