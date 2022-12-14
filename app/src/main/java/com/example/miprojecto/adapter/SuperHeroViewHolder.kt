package com.example.miprojecto.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.miprojecto.SuperHero
import com.example.miprojecto.databinding.ItemIconBinding
import com.example.miprojecto.databinding.ItemSuperheroBinding

class SuperHeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemSuperheroBinding.bind(view)

    fun render(superHeroModel: SuperHero, onClickListener: (SuperHero) -> Unit, onClickDeleted: (Int) -> Unit) {
        binding.tvCategoria.text = superHeroModel.categoria
        binding.tvDescripcion.text = superHeroModel.descripcion
        binding.tvCash.text = superHeroModel.cash
        binding.tvFecha.text = superHeroModel.fechaActual
        binding.ivSuperHero.setImageResource(superHeroModel.photo)
        //Glide.with(photo.context).load(superHeroModel.photo).into(photo)
       // itemView es para elegir el clicl completo
        itemView.setOnClickListener { onClickListener(superHeroModel) }
       binding.btnDelete.setOnClickListener { onClickDeleted(adapterPosition) }
    }
}