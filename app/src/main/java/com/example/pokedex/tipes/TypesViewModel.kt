package com.example.pokedex.tipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.PokeList
import com.example.pokedex.repository.http.HttpRepository
import kotlinx.coroutines.launch

class TypesViewModel(private val repository: HttpRepository) : ViewModel() {

    private val listTypes = mutableListOf<PokeList>()

    val onListIsReady = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            getPokeTypes()
        }
    }

    fun getListTypes() = listTypes

    private suspend fun getPokeTypes() {

        val response = repository.listTypes()

        if (response.isSuccessful) {
            response.body()?.listTypes?.forEach {

                if (it.name != "unknown" && it.name != "shadow") {
                    val pokeList = PokeList()

                    pokeList.name = it.name
                    pokeList.url = it.url

                    listTypes.add(pokeList)
                }
            }

            onListIsReady.value = true
        }
    }
}