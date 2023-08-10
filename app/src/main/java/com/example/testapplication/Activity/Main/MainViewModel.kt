package com.example.testapplication.Activity.Main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.Repository.RemoteRepository
import androidx.lifecycle.MutableLiveData
import com.example.testapplication.CustonView.LoadingView
import com.example.testapplication.Repository.DataRepository
import com.google.gson.Gson
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
    }

    fun getPokeListByIdOrder(): MutableList<Pokemon> {
        val set = mutableSetOf<Pokemon>()
        pokeMap.value?.forEach { (_, list) ->
            for (pokemon in list) {
                set.add(pokemon)
            }
        }
        return set.sortedBy { it.id }.toMutableList()
    }

    fun changePokemonCollectStatus(id:String){
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