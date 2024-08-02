package com.example.pokemonbook.ui.mainframe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonbook.R
import com.example.pokemonbook.databinding.ItemCategoryBinding
import com.example.pokemonbook.model.Pokemon
import com.example.pokemonbook.utils.ColorHelper
import com.example.pokemonbook.model.PokemonGroupWrapper
import com.example.pokemonbook.utils.ScreenUtils
import com.example.pokemonbook.utils.ViewAnimatorUtil

class GroupAdapter(
    private val selectAction: SelectAction,
) : ListAdapter<PokemonGroupWrapper, GroupAdapter.GroupViewHolder>(GroupDiffCallBack()) {
    private val singleItemWidthDp = 200

    interface SelectAction {
        fun onShowDetail(pokemonId: String)
        fun onGroupBackGroundClick(type: String)
        fun onCollectPokemon(id: String)
    }

    private val innerAdapterMap = mutableMapOf<String, PokeListAdapter>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class GroupViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val clickListener: View.OnClickListener = View.OnClickListener {
            val currentPokemon = getItem(adapterPosition)
            currentPokemon.isOpen = !currentPokemon.isOpen
            selectAction.onGroupBackGroundClick(
                currentPokemon.type
            )
            changeOpenState()
        }

        fun bind(pokemonGroup: PokemonGroupWrapper) {
            with(binding) {
                textViewCategory.text = pokemonGroup.type
                innerRecyclerView.isNestedScrollingEnabled = false
                innerRecyclerView.visibility = if (pokemonGroup.isOpen) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
                setAdjustGridLayoutManger()
                getChangedColorDrawable(pokemonGroup.type)?.let {
                    bg.background = it
                }
                bg.setOnClickListener(clickListener)
            }
            binding.innerRecyclerView.apply {
                val innerAdapter =
                    innerAdapterMap.getOrDefault(pokemonGroup.type, PokeListAdapter(selectAction))
                adapter = innerAdapter
                innerAdapter.submitList(pokemonGroup.pokemonList)
            }
        }

        private fun setAdjustGridLayoutManger() {
            val spanCount =
                (ScreenUtils.getScreenWidthDp(this.binding.root.context) / singleItemWidthDp).toInt()
            val gridLayoutManager =
                GridLayoutManager(this.binding.root.context, 1.coerceAtLeast(spanCount))
            gridLayoutManager.initialPrefetchItemCount = 4
            binding.innerRecyclerView.layoutManager = gridLayoutManager
            binding.innerRecyclerView.setHasFixedSize(true)
        }

        private fun getChangedColorDrawable(type: String): Drawable? {
            val bgDrawable = ResourcesCompat.getDrawable(
                binding.root.context.resources,
                R.drawable.category_radius_background,
                null
            ) ?: return null
            val color = ContextCompat.getColor(
                binding.root.context,
                ColorHelper.getColor(type)
            )
            setColorFilter(bgDrawable, color)
            return bgDrawable
        }

        private fun changeOpenState() {
            if (binding.innerRecyclerView.visibility == View.VISIBLE) {
                ViewAnimatorUtil.collapse(binding.innerRecyclerView)
            } else {
                ViewAnimatorUtil.expand(binding.innerRecyclerView)
            }
        }

        private fun setColorFilter(drawable: Drawable, @ColorInt color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
            } else {
                @Suppress("DEPRECATION")
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }
        }

    }

    private class GroupDiffCallBack : DiffUtil.ItemCallback<PokemonGroupWrapper>() {
        override fun areItemsTheSame(
            oldItem: PokemonGroupWrapper,
            newItem: PokemonGroupWrapper
        ): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(
            oldItem: PokemonGroupWrapper,
            newItem: PokemonGroupWrapper
        ): Boolean {
            return (oldItem.isOpen == newItem.isOpen
                    && oldItem.pokemonList.containsAll(newItem.pokemonList)
                    && newItem.pokemonList.containsAll(oldItem.pokemonList))
                    && isSameOrder(oldItem.pokemonList, newItem.pokemonList)
        }

        private fun isSameOrder(
            oldItem: MutableList<Pokemon>,
            newItem: MutableList<Pokemon>
        ): Boolean {
            if (oldItem.size != newItem.size) return false
            return oldItem.zip(newItem).all { (oldItem, newItem) -> oldItem.id == newItem.id }
        }

    }

}