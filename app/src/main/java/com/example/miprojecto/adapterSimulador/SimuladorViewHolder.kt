package com.example.miprojecto.adapterSimulador

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.miprojecto.SimuladorInversion
import com.example.miprojecto.SuperHero
import com.example.miprojecto.databinding.ItemSimuladorBinding
import com.example.miprojecto.databinding.ItemSuperheroBinding

class SimuladorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemSimuladorBinding.bind(view)

    fun render(simuladorModel: SimuladorInversion) {

        binding.idMonth.text = simuladorModel.mont
        binding.idCash.text = simuladorModel.cash
    }
}