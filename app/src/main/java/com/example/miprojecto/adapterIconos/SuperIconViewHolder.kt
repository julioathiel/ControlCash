package com.example.miprojecto.adapterIconos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.miprojecto.SuperIconos
import com.example.miprojecto.databinding.ItemIconBinding

class SuperIconViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val binding = ItemIconBinding.bind(view)

    fun render(superIconModel: SuperIconos, onClickListener: (SuperIconos) -> Unit){
        binding.photoIcono.setImageResource(superIconModel.photo)
        itemView.setOnClickListener { onClickListener(superIconModel) }
    }
}