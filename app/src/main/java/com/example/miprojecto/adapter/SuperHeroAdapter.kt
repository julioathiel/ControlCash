package com.example.miprojecto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miprojecto.R
import com.example.miprojecto.SuperHero
import com.example.miprojecto.SuperHeroProvider.Companion.superHeroList

class SuperHeroAdapter(private var superHeroList: List<SuperHero>, private val onClickListener: (SuperHero) -> Unit, private val onClickDeleted: (Int) -> Unit) :
    RecyclerView.Adapter<SuperHeroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SuperHeroViewHolder(layoutInflater.inflate(R.layout.item_superhero, parent, false))
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        val item = superHeroList[position]
        holder.render(item, onClickListener, onClickDeleted)
    }

    override fun getItemCount(): Int = superHeroList.size

    fun updateMovesFilter(superHeroList: List<SuperHero>) {
        this.superHeroList = superHeroList
        notifyDataSetChanged()
    }

}