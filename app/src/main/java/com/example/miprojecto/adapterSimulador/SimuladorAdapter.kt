package com.example.miprojecto.adapterSimulador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miprojecto.R
import com.example.miprojecto.SimuladorInversion
import com.example.miprojecto.SuperHero
import com.example.miprojecto.SuperHeroProvider.Companion.superHeroList

class SimuladorAdapter(private var simuladorList: List<SimuladorInversion>) :
    RecyclerView.Adapter<SimuladorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimuladorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SimuladorViewHolder(layoutInflater.inflate(R.layout.item_simulador, parent, false))
    }

    override fun onBindViewHolder(holder: SimuladorViewHolder, position: Int) {
        val item = simuladorList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = simuladorList.size

}