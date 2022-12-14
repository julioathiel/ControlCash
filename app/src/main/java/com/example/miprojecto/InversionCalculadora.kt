package com.example.miprojecto


import android.icu.text.Collator.getDisplayName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miprojecto.SimuladorProvider.Companion.simuladorList
import com.example.miprojecto.adapterSimulador.SimuladorAdapter
import com.example.miprojecto.databinding.ActivityInversionCalculadoraBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList


class InversionCalculadora : AppCompatActivity() {

    lateinit var binding: ActivityInversionCalculadoraBinding
    private lateinit var adapter: SimuladorAdapter
    private var simuladorMutableList: MutableList<SimuladorInversion> = simuladorList.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInversionCalculadoraBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnSimulador.setOnClickListener {
            val tna = binding.etTna.text.toString()
            val monto = binding.etMonto.text.toString()
            binding.tvDay.text = day(tna.toDouble(), monto.toDouble())
            binding.tvSemana.text = semana(tna.toDouble(), monto.toDouble())
            month(tna.toDouble(), monto.toDouble())
        }

        initRecyclerView()

    }

    private fun initRecyclerView() {
        adapter = SimuladorAdapter(simuladorList = simuladorMutableList)
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.recyclerSimulador.layoutManager = manager
        binding.recyclerSimulador.adapter = adapter
        binding.recyclerSimulador.addItemDecoration(decoration)
    }

    private fun day(tna: Double, monto: Double): String {
        val dia = ((tna / 100.0) / 365) * monto
        return imprimir(dia).toString()
    }

    private fun semana(tna: Double, monto: Double): String {
        val semana = (day(tna, monto).toDouble()) * 7
        return imprimir(semana).toString()

    }

    private fun month(tna: Double, monto: Double): String {
        var total = monto
        var month = LocalDate.now().month
        val data = ArrayList<SimuladorInversion>()
        for (i in 1..12) {
            month = month.plus(1)
            val diaTna = ((tna / 100.0) / 365) * total
            val mes = diaTna * 30.0
            total += mes
            data.add(SimuladorInversion(month.getDisplayName(TextStyle.FULL, Locale.getDefault()), "$ ${imprimir(total).toString()}"))
        }


        adapter = SimuladorAdapter(data)
        val manager = LinearLayoutManager(this)
        binding.recyclerSimulador.adapter = adapter
        return manager.toString()
    }

    private fun imprimir(mes: Double): BigDecimal? {
        return mes.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)

    }


}