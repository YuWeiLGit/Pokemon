package com.example.testapplication.Activity.Detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.IntentCompat
import com.bumptech.glide.Glide
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.Utils.ColorHelper
import com.example.testapplication.Utils.Constant
import com.example.testapplication.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var targetPokemon: Pokemon? = null
    private val viewModel: DetailViewModel by viewModels()
    private val TOTAL_POKEMON_SIZE = 809
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        targetPokemon =
            IntentCompat.getParcelableExtra(intent, Constant.targetPokemon, Pokemon::class.java)
        setContentView(binding.root)
        targetPokemon?.let {
            setPokemonView(pokemon = it)
        }
        setClick()
        observeViewModel()
    }

    private fun setClick() {
        binding.ivNext.setOnClickListener { _ ->
            targetPokemon?.let {
                viewModel.getPokemonById(it.getNextId())
            }
        }
        binding.ivPrevious.setOnClickListener { _ ->
            targetPokemon?.let {
                viewModel.getPokemonById(it.getPreviousId())
            }
        }
        binding.ivHeart.setOnClickListener { _ ->
            targetPokemon?.let {
                binding.ivHeart.isActivated = !it.isCollect
                viewModel.changePokemonCollectStatus(it.id)
            }
        }

    }


    private fun Pokemon.getPreviousId(): String {
        val currentId = this.id
        val remove = currentId.removePrefix("#")
        val id = remove.toInt() - 1
        if (id == 0) {
            return "#$TOTAL_POKEMON_SIZE"
        }
        return "#${"%03d".format(id)}"

    }

    private fun Pokemon.getNextId(): String {
        val currentId = this.id
        val remove = currentId.removePrefix("#")
        val id = remove.toInt() + 1
        if (id == TOTAL_POKEMON_SIZE + 1) {
            return "#001"
        }
        return "#${"%03d".format(id)}"
    }

    private fun observeViewModel() {
        this.viewModel.let {
            it.loadingViewLiveData.observe(this@DetailActivity) { status ->
                binding.loadingView.setStatus(status)
            }
            it.targetPokemon.observe(this@DetailActivity) { target ->
                this.targetPokemon = target
                setPokemonView(target)
            }

        }
    }


    private fun setPokemonView(pokemon: Pokemon) {
        val type = pokemon.typeOfPokemon?.get(0)
        type?.let {
            binding.topContent.setBackgroundColor(Color.parseColor(ColorHelper.getColor(it)))
        }
        binding.description.text = pokemon.xDescription
        binding.tvHeight.text = String.format("Height%s", pokemon.height)
        binding.tvWeight.text = String.format("Weight%s", pokemon.weight)
        binding.textTotal.text = pokemon.total.toString()
        binding.textHp.text = pokemon.hp.toString()
        binding.textAtk.text = pokemon.attack.toString()
        binding.textDef.text = pokemon.defense.toString()
        binding.textSpd.text = pokemon.speed.toString()
        binding.ivHeart.isActivated = pokemon.isCollect
        Glide.with(this)
            .load(pokemon.imageUrl)
            .into(binding.ivPokemon)

    }

}