package com.example.miprojecto

import android.graphics.Paint
import android.icu.lang.UCharacter.VerticalOrientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miprojecto.adapterIconos.SuperIconAdapter
import com.example.miprojecto.databinding.ActivityCreateSpinnerBinding

class ActivityCreateSpinner : AppCompatActivity() {

    private var superIconMutableList: MutableList<SuperIconos> =
        SuperIconProvider.superIconList.toMutableList()
    private lateinit var adapter: SuperIconAdapter

    private lateinit var binding:ActivityCreateSpinnerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = SuperIconAdapter(
            superIconList = superIconMutableList,
            onClickListener = { superhero -> onItemSelected(superhero) }
        )


        val manager = GridLayoutManager(this,4)
         val decoration = DividerItemDecoration(this, manager.orientation)
        binding.recyclerSuperIcon.layoutManager = manager
        binding.recyclerSuperIcon.adapter = adapter
        // binding.recyclerSuperIcon.addItemDecoration(decoration)
    }

    private fun onItemSelected(superIcon: SuperIconos) {
        Toast.makeText(this, superIcon.photo, Toast.LENGTH_SHORT).show()
    }

    private fun onDeletedItem(position: Int) {
        superIconMutableList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
}