package com.example.testapplication.Activity.Main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testapplication.Adapter.CategoryAdapter
import com.example.testapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: CategoryAdapter
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCLick()
        adapter = CategoryAdapter()
        binding.mainRecyclerview.adapter = adapter
        initViewModel()
        viewModel.getData()
    }


    private fun initViewModel() {
        viewModel.apply {
            topBarStatus.observe(this@MainActivity) {
                changeStatusView(it)
            }
            pokeMap.observe(this@MainActivity) {
                adapter.changeMap(it)
            }
        }
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