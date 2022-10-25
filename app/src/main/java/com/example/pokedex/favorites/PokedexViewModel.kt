package com.example.pokedex.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.model.PokeList
import com.example.pokedex.repository.PokedexRepository

class PokedexViewModel(private val repository: PokedexRepository): ViewModel() {

    fun getListFav(): LiveData<List<PokeList>?> {
        return repository.buscar()
    }
}