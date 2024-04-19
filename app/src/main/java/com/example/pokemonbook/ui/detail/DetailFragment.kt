package com.example.pokemonbook.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pokemonbook.databinding.FragmentDetailBinding
import com.example.pokemonbook.model.Pokemon
import com.example.pokemonbook.ui.mainframe.MainViewModel
import com.example.pokemonbook.utils.ColorHelper

const val TARGET_POKEMON_ID = "targetPokemonId"

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var currentDisplay: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchTargetIdAndUpdateView()
        setClick()
        observeViewModel()
    }

    private fun setClick() {
        binding.ivNext.setOnClickListener {
            viewModel.toNextPokemon()
        }
        binding.ivPrevious.setOnClickListener {
            viewModel.toPreviousPokemon()
        }
        binding.ivHeart.setOnClickListener { _ ->
            currentDisplay?.let {
                binding.ivHeart.isActivated = !it.isCollect
                viewModel.changePokemonCollectStatus(it.id)
            }
        }
    }

    private fun fetchTargetIdAndUpdateView() {
        val id = arguments?.getString(TARGET_POKEMON_ID)
        id?.let {
            viewModel.getPokemonById(it)
        } ?: displayErrorView()
    }

    private fun observeViewModel() {
        with(viewModel) {
            loadingViewLiveData.observe(viewLifecycleOwner) { status ->
                binding.loadingView.setStatus(status)
            }
            targetPokemon.observe(viewLifecycleOwner) { target ->
                target?.let { pokemon ->
                    setPokemonView(pokemon)
                } ?: displayErrorView()
            }
        }
    }

    private fun displayErrorView() {

    }

    private fun setPokemonView(pokemon: Pokemon) {
        currentDisplay = pokemon
        val typeList = pokemon.typeOfPokemon
        typeList?.forEachIndexed { index, type ->
            if (index == 0) {
                binding.firstType.setBackgroundResource(ColorHelper.getColor(type))
                binding.secondType.visibility = View.GONE
            } else if (index == 1) {
                binding.secondType.setBackgroundResource(ColorHelper.getColor(type))
                binding.secondType.visibility = View.VISIBLE
            }
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
        binding.ivPokemon.startLoadImage(pokemon.imageUrl)
    }

}