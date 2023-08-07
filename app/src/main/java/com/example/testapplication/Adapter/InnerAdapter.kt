package com.example.testapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.R
import com.example.testapplication.databinding.ItemInnerPokemonBinding

class InnerAdapter : RecyclerView.Adapter<InnerAdapter.InnerViewHolder>() {

    private var list = listOf<Pokemon>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        return InnerViewHolder(
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

    fun changeList(list: List<Pokemon>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val pokemon = list[position]
        holder.binding.apply {
            name.text = pokemon.name
            id.text = String.format("# %s", pokemon.id)
            atk.text = String.format("ATK %d", pokemon.attack)
            def.text = String.format("DEF %d", pokemon.defense)
            spd.text = String.format("SPD %d", pokemon.speed)
//            Glide.with(holder.binding.root.context)
//                .load(pokemon.imageUrl)
//                .into(imageView)

        }
    }

    class InnerViewHolder(binding: ItemInnerPokemonBinding) :
        RecyclerView.ViewHolder(binding.root){
        val binding: ItemInnerPokemonBinding

        init {
            this.binding = binding
        }



    }
}