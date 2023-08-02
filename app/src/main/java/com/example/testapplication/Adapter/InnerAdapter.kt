package com.example.testapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.R
import com.example.testapplication.databinding.ItemCategoryBinding
import com.example.testapplication.databinding.ItemInnerPokemonBinding

class InnerAdapter : RecyclerView.Adapter<InnerAdapter.InnerViewHolder>() {

    private var list = listOf<Pokemon>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        return InnerAdapter.InnerViewHolder(
            ItemInnerPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class InnerViewHolder(binding: ItemInnerPokemonBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        val binding: ItemInnerPokemonBinding

        init {
            this.binding = binding
            binding.heart.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v?.id == R.id.heart) {

            }
        }

    }
}