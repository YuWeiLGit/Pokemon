package com.example.pokemonbook.ui.mainframe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonbook.model.Pokemon
import androidx.lifecycle.MutableLiveData
import com.example.pokemonbook.custonView.LoadingView
import com.example.pokemonbook.model.ApiResult
import com.example.pokemonbook.model.PokemonGroupWrapper
import com.example.pokemonbook.data.repository.DataRepository
import com.example.pokemonbook.utils.OnceEvent
import com.example.pokemonbook.utils.PokemonSortHelper
import com.example.pokemonbook.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    val topBarStatus = MutableLiveData<Status>()
    val loadingViewLiveData = MutableLiveData<Int>()
    var targetPokemon = MutableLiveData<Pokemon?>()
    val toDetailOnceEventLiveData = MutableLiveData<OnceEvent<String?>>()
    val pokemonGroupLiveData = MutableLiveData<MutableList<PokemonGroupWrapper>>()
    val errorViewLivaData = MutableLiveData<String?>()
    private val visibleSet = mutableSetOf<String>()

    fun getPokemonById(id: String) {
        loadingViewLiveData.postValue(LoadingView.SHOW_WHITE_LOADING)
        viewModelScope.launch {
            repository.getPokemonById(id).catch {
                loadingViewLiveData.postValue(LoadingView.NO_DATA)
                errorViewLivaData.postValue("CAN NOT GET RESULT")
                Log.d("GET DATA ERROR", "CAN NOT GET RESULT")
            }.collect {
                loadingViewLiveData.postValue(LoadingView.HIDE_LOADING)
                errorViewLivaData.postValue(null)
                targetPokemon.postValue(it)
            }
        }
    }

    fun toPreviousPokemon() {
        val currentPokemon = targetPokemon.value
        currentPokemon?.let {
            val queryId = if (it.id.removePrefix("#").toInt() - 1 == 0) {
                repository.getTotalPokemonSize() ?: 0
            } else {
                it.id.removePrefix("#").toInt() - 1
            }
            getPokemonById("#${"%03d".format(queryId)}")
        }
    }


    fun toNextPokemon() {
        val currentPokemon = targetPokemon.value
        currentPokemon?.let {
            val queryId = if (it.id.removePrefix("#").toInt() == repository.getTotalPokemonSize()) {
                1
            } else {
                it.id.removePrefix("#").toInt() + 1
            }
            getPokemonById("#${"%03d".format(queryId)}")
        }
    }

    fun changeCurrentTopBarStatus(status: Status) {
        topBarStatus.postValue(status)
        pokemonGroupLiveData.value?.let {
            startSortPokemon(it, status)
        }
    }

    private fun startSortPokemon(group: MutableList<PokemonGroupWrapper>, status: Status) {
        val tmp = mutableListOf<PokemonGroupWrapper>()
        group.forEach { tmp.add(it.copy()) }
        tmp.forEach { wrapper ->
            val sortList = when (status) {
                Status.DEFAULT -> wrapper.pokemonList.sortedBy {
                    it.id
                }

                Status.ATK -> wrapper.pokemonList.sortedWith(compareBy({ it.attack }, { it.id }))

                Status.DEF -> wrapper.pokemonList.sortedWith(compareBy({ it.defense }, { it.id }))
                Status.SPD -> wrapper.pokemonList.sortedWith(compareBy({ it.speed }, { it.id }))
            }
            wrapper.pokemonList = sortList.toMutableList()
        }
        pokemonGroupLiveData.postValue(tmp)
    }

    fun changePokemonCollectStatus(id: String) {
        viewModelScope.launch {
            repository.changePokemonCollectStatus(id)
        }
    }

    fun getData() {
        loadingViewLiveData.postValue(LoadingView.SHOW_WHITE_LOADING)
        viewModelScope.launch {
            repository.getPokemonData().catch {
                loadingViewLiveData.postValue(LoadingView.NO_NETWORK)
                errorViewLivaData.postValue("NETWORK ERROR")
                Log.d("GET DATA ERROR", "CAN NOT GET RESULT")
            }.collect { rawData ->
                when (rawData) {
                    is ApiResult.Failed -> {
                        loadingViewLiveData.postValue(LoadingView.NO_DATA)
                        val errorMessage = rawData.message ?: "Failed get Data"
                        errorViewLivaData.postValue(errorMessage)
                        Log.d("GET DATA ERROR", errorMessage)
                    }

                    is ApiResult.Success -> {
                        rawData.data?.let {
                            val map = PokemonSortHelper.sortByPokemonType(it)
                            val compute = map.map { m ->
                                PokemonGroupWrapper(
                                    m.key,
                                    m.value.toMutableList(),
                                    visibleSet.contains(m.key)
                                )
                            }.toMutableList()

                            if (map.isEmpty()) {
                                loadingViewLiveData.postValue(LoadingView.NO_DATA)
                                errorViewLivaData.postValue("EMPTY DATA")
                                Log.d("GET DATA ERROR", "EMPTY DATA")
                            } else {
                                errorViewLivaData.postValue(null)
                                loadingViewLiveData.postValue(LoadingView.HIDE_LOADING)
                            }


                            val status = topBarStatus.value ?: Status.DEFAULT
                            startSortPokemon(compute, status)
                        }
                    }
                }
            }

        }
    }


    fun changeOpenState(type: String) {
        visibleSet.addOrDelete(type)
    }

    private fun MutableSet<String>.addOrDelete(key: String) {
        if (key in this) {
            this.remove(key)
        } else {
            this.add(key)
        }
    }

}