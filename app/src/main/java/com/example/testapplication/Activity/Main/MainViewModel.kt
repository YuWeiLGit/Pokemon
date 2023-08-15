package com.example.testapplication.Activity.Main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.Model.Pokemon
import androidx.lifecycle.MutableLiveData
import com.example.testapplication.CustonView.LoadingView
import com.example.testapplication.Repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    val topBarStatus = MutableLiveData<MainActivity.Status>()
    val pokeMap = MutableLiveData(mutableMapOf<String, List<Pokemon>>())
    val loadingViewLiveData = MutableLiveData<Int>()
    fun changeCurrentTopBarStatus(status: MainActivity.Status) {
        topBarStatus.postValue(status)
        sortPokemon(status)
    }

    private fun sortPokemon(status: MainActivity.Status) {
        val map = pokeMap.value
        map?.forEach { (type, list) ->
            val sortList = when (status) {
                MainActivity.Status.DEFAULT -> list.sortedBy { it.id }
                MainActivity.Status.ATK -> list.sortedBy { it.attack }
                MainActivity.Status.DEF -> list.sortedBy { it.defense }
                MainActivity.Status.SPD -> list.sortedBy { it.speed }
            }
            map[type] = sortList
        }
        pokeMap.postValue(map)
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
                Log.d("GET DATA ERROR", "CAN NOT GET RESULT")
            }.collect { map ->
                pokeMap.postValue(map)
                if (map.isEmpty()) {
                    loadingViewLiveData.postValue(LoadingView.NO_DATA)
                } else {
                    loadingViewLiveData.postValue(LoadingView.HIDE_LOADING)
                }
            }

        }
    }

}