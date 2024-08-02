package com.example.pokemonbook.ui.mainframe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.pokemonbook.databinding.FragmentGroupBinding
import com.example.pokemonbook.ui.mainframe.adapter.GroupAdapter
import com.example.pokemonbook.utils.OnceEvent
import com.example.pokemonbook.utils.Status

class GroupFragment : NavHostFragment() {
    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private var adapter: GroupAdapter = GroupAdapter(object : GroupAdapter.SelectAction {
        override fun onShowDetail(pokemonId: String) {
            viewModel.toDetailOnceEventLiveData.postValue(OnceEvent(pokemonId))
        }

        override fun onGroupBackGroundClick(type: String) {
            viewModel.changeOpenState(type)
        }

        override fun onCollectPokemon(id: String) {
            viewModel.changePokemonCollectStatus(id)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClick()
        viewmodelSetObserver()
        initRecyclerview()
        fetchData()
    }


    private fun viewmodelSetObserver() {
        viewModel.apply {
            topBarStatus.observe(viewLifecycleOwner) {
                changeStatusView(it)
            }
            pokemonGroupLiveData.observe(viewLifecycleOwner) { groupList ->
                adapter.submitList(groupList.toMutableList())
            }
            loadingViewLiveData.observe(viewLifecycleOwner) {
                binding.loadingView.setStatus(it)
            }
        }
    }

    private fun initRecyclerview() {
        binding.mainRecyclerview.adapter = adapter
    }

    private fun changeStatusView(status: Status) {
        val statusToViewMap = mapOf(
            Status.ATK to binding.attack,
            Status.DEFAULT to binding.Default,
            Status.DEF to binding.defense,
            Status.SPD to binding.spd
        )
        val selectedView = statusToViewMap[status]
        for (view in statusToViewMap.values) {
            view.setSelect(view == selectedView)
        }
    }

    private fun fetchData() {
        viewModel.getData()
    }


    private fun setClick() {
        binding.spd.setOnClickListener { _ ->
            viewModel.changeCurrentTopBarStatus(Status.SPD)
        }
        binding.attack.setOnClickListener { _ ->
            viewModel.changeCurrentTopBarStatus(Status.ATK)
        }
        binding.defense.setOnClickListener { _ ->
            viewModel.changeCurrentTopBarStatus(Status.DEF)
        }
        binding.Default.setOnClickListener { _ ->
            viewModel.changeCurrentTopBarStatus(Status.DEFAULT)
        }
    }
}