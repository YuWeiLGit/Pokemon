package com.example.testapplication.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.Activity.Main.MainActivity
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.Utils.ColorHelper
import com.example.testapplication.databinding.ItemCategoryBinding

class CategoryAdapter(val selectPokemon: MainActivity.SelectPokemon) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var map = mutableMapOf<String, List<Pokemon>>().toList()
    private val innerAdapterMap = mutableMapOf<String, InnerAdapter>()
    private var excludeTab = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return map.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val binding = holder.binding
        if (map[position].first == excludeTab) {
            binding.innerRecyclerView.visibility = View.VISIBLE
        }else{
            binding.innerRecyclerView.visibility = View.GONE
        }
        binding.textViewCategory.text = map[position].first
        binding.bg.setBackgroundColor(Color.parseColor(ColorHelper.getColor(map[position].first)))
        binding.innerRecyclerView.adapter = innerAdapterMap.getOrDefault(
            map[position].first,
            InnerAdapter(emptyList(), selectPokemon)
        )
    }

    fun changeMap(map: MutableMap<String, List<Pokemon>>) {
        this.map = map.toList()
        setInnerAdapterData(map)
        notifyDataSetChanged()
    }

    fun tmp(id:String){
        excludeTab = id
        notifyDataSetChanged()
    }

    private fun setInnerAdapterData(map: MutableMap<String, List<Pokemon>>) {
        for ((key, value) in map) {
            val innerAdapter = innerAdapterMap.getOrDefault(key, InnerAdapter(value, selectPokemon))
            innerAdapterMap[key] = innerAdapter
        }
    }

    inner class CategoryViewHolder(binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: ItemCategoryBinding
        private val clickListener = View.OnClickListener {
            val innerRecyclerView = binding.innerRecyclerView
            if (innerRecyclerView.visibility == View.VISIBLE) {
                tmp("")
            } else {
                tmp(map[adapterPosition].first)
                innerRecyclerView.visibility = View.VISIBLE
            }
        }

        init {
            this.binding = binding
            this.binding.bg.setOnClickListener(clickListener)
        }


    }
}