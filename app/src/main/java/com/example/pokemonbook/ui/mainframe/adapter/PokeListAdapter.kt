package com.example.pokemonbook.ui.mainframe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonbook.databinding.ItemInnerPokemonBinding
import com.example.pokemonbook.model.Pokemon

class PokeListAdapter(private val selectPokemon: GroupAdapter.SelectAction) :
    ListAdapter<Pokemon, PokeListAdapter.PokemonViewHolder>(PokemonDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemInnerPokemonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    inner class PokemonViewHolder(private val binding: ItemInnerPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.apply {
                name.text = pokemon.name
                id.text = String.format("%s", pokemon.id)
                atk.text = String.format("ATK %d", pokemon.attack)
                def.text = String.format("DEF %d", pokemon.defense)
                spd.text = String.format("SPD %d", pokemon.speed)
                imageView.startLoadImage(pokemon.imageUrl)
                heart.isActivated = pokemon.isCollect
            }
            setClick()
        }

        private fun setClick() {
            binding.heart.setOnClickListener {
                val pokemon = getItem(adapterPosition)
                pokemon.id.let {
                    selectPokemon.onCollectPokemon(pokemon.id)
                    binding.heart.isActivated = !pokemon.isCollect
                }
            }
            binding.innerBg.setOnClickListener {
                val pokemon = getItem(adapterPosition)
                selectPokemon.onShowDetail(pokemon.id)
            }
        }
    }

    private class PokemonDiffCallBack : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

    }


}