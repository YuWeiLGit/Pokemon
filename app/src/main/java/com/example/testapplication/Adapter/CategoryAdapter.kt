package com.example.testapplication.Adapter

import android.graphics.Color
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.R
import com.example.testapplication.Utils
import com.example.testapplication.databinding.ItemCategoryBinding
import okhttp3.internal.notify

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var map = mutableMapOf<String, List<Pokemon>>().toList()
    private var innerAdapter: InnerAdapter = InnerAdapter()

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
        binding.textViewCategory.text = map[position].first
        binding.bg.setBackgroundColor(Color.parseColor(Utils.getColor(map[position].first)))
        binding.innerRecyclerView.adapter = innerAdapter
        innerAdapter.changeList(map[position].second)
    }

    fun changeMap(map: MutableMap<String, List<Pokemon>>) {
        this.map = map.toList()
        notifyDataSetChanged()
    }

    class CategoryViewHolder(binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        val binding: ItemCategoryBinding

        init {
            this.binding = binding
            binding.bg.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v?.id == R.id.bg ||v?.id == R.id.textView_category) {
                binding.innerRecyclerView.visibility =
                    if (binding.innerRecyclerView.visibility == View.VISIBLE) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
            }
        }


    }
}