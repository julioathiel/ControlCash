package com.example.miprojecto.adapterIconos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miprojecto.R
import com.example.miprojecto.SuperIconos

class SuperIconAdapter(private var superIconList: List<SuperIconos>, private val onClickListener: (SuperIconos) -> Unit): RecyclerView.Adapter<SuperIconViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperIconViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SuperIconViewHolder(layoutInflater.inflate(R.layout.item_icon, parent, false))
    }

    override fun onBindViewHolder(holder: SuperIconViewHolder, position: Int) {
       val item = superIconList[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int = superIconList.size

}