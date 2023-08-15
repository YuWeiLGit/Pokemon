package com.example.testapplication.Activity.Detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.CustonView.LoadingView
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.Repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    val targetPokemon = MutableLiveData<Pokemon>()
    val loadingViewLiveData = MutableLiveData<Int>()

    fun getPokemonById(id: String) {
        loadingViewLiveData.postValue(LoadingView.SHOW_WHITE_LOADING)
        viewModelScope.launch {
            dataRepository.getPokemonById(id).catch {
                loadingViewLiveData.postValue(LoadingView.NO_DATA)
                Log.d("GET DATA ERROR", "CAN NOT GET RESULT")
            }.collect {
                loadingViewLiveData.postValue(LoadingView.HIDE_LOADING)
                targetPokemon.postValue(it)
            }
        }
    }

    fun changePokemonCollectStatus(id: String) {
        viewModelScope.launch {
            dataRepository.changePokemonCollectStatus(id)
        }
    }
}