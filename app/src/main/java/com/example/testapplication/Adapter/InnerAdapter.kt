package com.example.testapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapplication.Activity.Main.MainActivity
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.databinding.ItemInnerPokemonBinding

class InnerAdapter(
    private var list: List<Pokemon>,
    private val selectPokemon: MainActivity.SelectPokemon
) :
    RecyclerView.Adapter<InnerAdapter.InnerViewHolder>() {

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


    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val pokemon = list[position]
        holder.binding.apply {
            name.text = pokemon.name
            id.text = String.format("%s", pokemon.id)
            atk.text = String.format("ATK %d", pokemon.attack)
            def.text = String.format("DEF %d", pokemon.defense)
            spd.text = String.format("SPD %d", pokemon.speed)
            Glide.with(holder.binding.root.context)
                .load(pokemon.imageUrl)
                .into(imageView)
            heart.isActivated = pokemon.isCollect
        }
    }


    fun changeData(list: List<Pokemon>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class InnerViewHolder(binding: ItemInnerPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: ItemInnerPokemonBinding
        private val heartClickListener = View.OnClickListener {
            val pokemon = list[adapterPosition]
            pokemon.id.let {
                selectPokemon.onCollectPokemon(pokemon.id)
                binding.heart.isActivated = !pokemon.isCollect
            }
        }

        private val detailClickListener = View.OnClickListener {
            selectPokemon.onShowDetail(list[adapterPosition])
        }

        init {
            this.binding = binding
            binding.heart.setOnClickListener(heartClickListener)
            binding.innerBg.setOnClickListener(detailClickListener)
        }


    }
}