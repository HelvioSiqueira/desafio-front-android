package com.example.pokedex.tipes

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.PokeList
import com.example.pokedex.repository.PokeRepository
import kotlinx.coroutines.launch

class ListPokeViewModel(private val repository: PokeRepository): ViewModel() {

    private val pokeListTypes = mutableListOf<PokeList>()

    val onListIsReady = MutableLiveData<Boolean>()

    fun getPokeList(url: String): MutableList<PokeList> {

        viewModelScope.launch {
            getPokeListTypesApi(url)
        }

        return pokeListTypes
    }

    private suspend fun getPokeListTypesApi(url: String){

        val response = repository.pokeListType(url)

        if (response.isSuccessful){
            response.body()?.pokeListTypes?.forEach {
                val pokeList = PokeList()

                pokeList.name = it.pokemon.name
                pokeList.url = it.pokemon.url

                Log.d("HSV", pokeList.name)

                pokeListTypes.add(pokeList)
            }

            onListIsReady.value = true
        }

    }
}