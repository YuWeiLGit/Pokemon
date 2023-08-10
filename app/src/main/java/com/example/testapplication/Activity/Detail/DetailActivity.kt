package com.example.testapplication.Activity.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.IntentCompat
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.R
import com.example.testapplication.Utils.Constant
import com.example.testapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var targetPokemon: Pokemon? = null
    private var pokemonList: ArrayList<Pokemon>? = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        targetPokemon =
            IntentCompat.getParcelableExtra(intent, Constant.targetPokemon, Pokemon::class.java)
        pokemonList = IntentCompat.getParcelableArrayListExtra(
            intent,
            Constant.pokemonList,
            Pokemon::class.java
        )
        setContentView(binding.root)
        tmp()
    }


    fun tmp() {
        binding.textView.text = pokemonList?.size.toString()
        binding.textView2.text = targetPokemon?.id
    }

}