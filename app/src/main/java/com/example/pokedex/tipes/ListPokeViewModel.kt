package com.example.pokedex.tipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.PokeList
import com.example.pokedex.repository.PokeRepository
import com.example.pokedex.util.URL_IMG
import kotlinx.coroutines.launch

class ListPokeViewModel(private val repository: PokeRepository) : ViewModel() {

    private val pokeListTypes = mutableListOf<PokeList>()

    val onListIsReady = MutableLiveData<Boolean>()

    fun getPokeList(url: String): MutableList<PokeList> {

        viewModelScope.launch {
            getPokeListTypesApi(url)
        }

        return pokeListTypes
    }

    private suspend fun getPokeListTypesApi(url: String) {

        val response = repository.pokeListType(url)

        if (response.isSuccessful) {
            response.body()?.pokeListTypes?.forEach {
                val pokeList = PokeList()

                pokeList.url = it.pokemon.url
                pokeList.id = pokeList.url.substringAfterLast("pokemon/").substringBeforeLast("/")

                if (pokeList.id.toInt() < 10000) {
                    pokeList.name = it.pokemon.name
                    pokeList.urlImg = "$URL_IMG${pokeList.id}.png"

                    pokeListTypes.add(pokeList)
                }
            }

            onListIsReady.value = true
        }

    }
}