package com.example.testapplication.Activity.Main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.internet.DataRepository
import com.example.testapplication.internet.Result
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    val topBarStatus = MutableLiveData<MainActivity.Status>()
    val pokeMap = MutableLiveData(mutableMapOf<String, List<Pokemon>>())
    fun changeCurrentTopBarStatus(status: MainActivity.Status) {
        topBarStatus.postValue(status)
    }

    fun getData() {
        viewModelScope.launch {
            repository.getPokemonData().catch {
                Log.d("GET DATA ERROR", "CAN NOT GET RESULT")
            }.collect { map ->
                pokeMap.postValue(map)
            }

        }
    }

}