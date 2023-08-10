package com.example.testapplication.Activity.Main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testapplication.Activity.Detail.DetailActivity
import com.example.testapplication.Adapter.CategoryAdapter
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.Utils.Constant
import com.example.testapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: CategoryAdapter
    private lateinit var binding: ActivityMainBinding

    interface SelectPokemon {
        fun onShowDetail(pokemon: Pokemon)

        fun onCollectPokemon(id: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCLick()
        adapter = CategoryAdapter(object : SelectPokemon {
            override fun onShowDetail(pokemon: Pokemon) {
                startDetailActivity(viewModel.getPokeListByIdOrder(), pokemon)
            }

            override fun onCollectPokemon(id: String) {
                viewModel.changePokemonCollectStatus(id)
            }
        })
        binding.mainRecyclerview.adapter = adapter
        initViewModel()
        fetchData()
    }


    fun startDetailActivity(list: MutableList<Pokemon>, targetPokemon: Pokemon) {
//        val intent = Intent(this, DetailActivity::class.java)
//        intent.putExtra(Constant.targetPokemon, targetPokemon)
//        intent.putParcelableArrayListExtra(Constant.pokemonList, ArrayList(list))
//        startActivity(intent)
    }

    private fun initViewModel() {
        viewModel.apply {
            topBarStatus.observe(this@MainActivity) {
                changeStatusView(it)
            }
            pokeMap.observe(this@MainActivity) {
                adapter.changeMap(it)
            }
            loadingViewLiveData.observe(this@MainActivity) {
                binding.loadingView.setStatus(it)
            }
        }
    }


    private fun fetchData() {
        viewModel.getData()
    }


    private fun setCLick() {
        binding.spd.setOnClickListener { _ ->
            viewModel.changeCurrentTopBarStatus(Status.SPD)
        }
        binding.attack.setOnClickListener { _ ->
            viewModel.changeCurrentTopBarStatus(Status.ATK)
        }
        binding.defense.setOnClickListener { _ ->
            viewModel.changeCurrentTopBarStatus(Status.DEF)
        }
        binding.total.setOnClickListener { _ ->
            viewModel.changeCurrentTopBarStatus(Status.TOTAL)
        }
    }


    private fun changeStatusView(status: Status) {
        val statusToViewMap = mapOf(
            Status.ATK to binding.attack,
            Status.TOTAL to binding.total,
            Status.DEF to binding.defense,
            Status.SPD to binding.spd
        )
        val selectedView = statusToViewMap[status]
        for (view in statusToViewMap.values) {
            view.setSelect(view == selectedView)
        }
    }


    enum class Status {
        ATK, DEF, SPD, TOTAL
    }
}