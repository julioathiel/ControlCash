package com.example.miprojecto


import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.miprojecto.databinding.ActivityDineroBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat


class ActivityDinero : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var activityContext: Context
    lateinit var binding: ActivityDineroBinding
    var acumulador = SaveDatos.prefs.getAcumuladorString()
    var current = ""
    private lateinit var mpSoundBoton: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDineroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSound()

        activityContext = this
        val s = binding.editTextNumber.text
        binding.btnGuardar.isEnabled = false



        binding.editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.btnGuardar.isEnabled = s.toString() != "" //inabilita el boton guardar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.btnGuardar.isEnabled = s.toString() != "" //inabilita el boton guardar

                if (s?.isEmpty()!!) return
                if (s.toString() != "") {
                    binding.editTextNumber.removeTextChangedListener(this)
                    val cleanString = s.replace("""[$,.]""".toRegex(), "")
                    val parsed = cleanString.toDouble()
                    val decimalFormat = NumberFormat.getCurrencyInstance().format((parsed / 100))
                    current = decimalFormat.replace("""[$,.]""".toRegex(), "")
                    binding.editTextNumber.setText(decimalFormat)//muestra $10.00
                    binding.editTextNumber.setSelection(decimalFormat.length)
                    binding.editTextNumber.addTextChangedListener(this)
                }
            }

        })

        //   current = stringFormat(diner.toString())

        binding.switchCompatIngreso.apply {
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    binding.editTextNumber.text?.clear()//anda bien
                    // dinero?.clear()
                } else {
                    binding.editTextNumber.text?.clear()


                }
            }
        }


        binding.btnGuardar.setOnClickListener {
            mpSoundBoton.start()
            if (s != null) {
                if (s.isEmpty()) {
                    Toast.makeText(this, "Ingrese dato", Toast.LENGTH_SHORT).show()
                } else if (binding.switchCompatIngreso.isChecked) {
                    val resultado = dinerSuma(current)
                    SaveDatos.prefs.saveAcumuladorString(resultado) // para resolver en esta activity
                    binding.editTextNumber.text?.clear()
                    Toast.makeText(this, "ingreso guardado", Toast.LENGTH_SHORT).show()
                } else {
                    val resultado = dinerResta(current)
                    SaveDatos.prefs.saveAcumuladorString(resultado)
                    if (resultado.toDouble() < 0) {
                        resetAcum()
                        alertDialog()
                        binding.editTextNumber.text?.clear()
                    } else {
                        // SaveDatos.prefs.saveAcumuladorString(resultado)
                        binding.editTextNumber.text?.clear()
                        Toast.makeText(this, " gasto guardado", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        spinnerCategoria()

    }

    private fun initSound() {
        mpSoundBoton = MediaPlayer.create(this,R.raw.sound1).apply {
            isLooping = false
        }
    }

    private fun spinnerCategoria() {
        val spinnerCategory =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)
        spinnerCategory.addAll(listOf("prueba1", "prueba2", "prueba3", "prueba4"))
        spinnerCategory.add("nuevo")
        binding.spinnerCategoria.onItemSelectedListener = this
        binding.spinnerCategoria.adapter = spinnerCategory
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent!!.getItemAtPosition(position)
        binding.tvSelected.text = selectedItem.toString()

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Use as per your wish
    }


    //ANDA PERFECTO
    private fun dinerSuma(cash: String): String {
        var inicio = 0.00
        //  inicio += cash//inicio  0.0  cash 1000.0
        //inicio += acumulador.toDouble()
        inicio += cash.toDouble() / 100 //10.0
        inicio += acumulador.toDouble()
        val star = BigDecimal(inicio)
        star.setScale(2, RoundingMode.HALF_EVEN)
        return star.toString()
    }

    //ANDA PERFECTO
    private fun dinerResta(cash: String): String {
        val resta = acumulador.toDouble() - cash.toDouble() / 100
        resta.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)
        //  resta -= resta // por si me paso de numeros negativos
        // val resta = acumulador.toDouble() - dinero.toDouble()
        return resta.toString()
    }


    private fun resetAcum(): String {
        val valor = 0.0
        val date = SaveDatos.prefs.saveAcumuladorString(valor.toString())
        return date.toString()
    }

    private fun alertDialog() {
        val builder = AlertDialog.Builder(this).apply {
            setTitle("Lo siento...")
            setMessage("no se puede guardar numero negativo, se reseteo a 0")
            setPositiveButton(android.R.string.ok) { dialog, wich ->
                //   Toast.makeText(this@ActivityDinero, "0.0", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton(android.R.string.cancel, null)
            // setNegativeButton("Cancel", null)
            //  setNeutralButton("recordar mas tarde", null)
        }
        builder.show()
    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_EditarSpinner -> startActivity(Intent(this, ActivityCreateSpinner::class.java))
        }
    }
}
