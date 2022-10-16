package com.example.pokedex.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.PokeList
import com.example.pokedex.Pokemon
import com.example.pokedex.repository.PokedexRepository
import com.example.pokedex.repository.room.RoomRepository

class PokedexViewModel(private val repository: PokedexRepository): ViewModel() {

    fun getListFav(): LiveData<List<PokeList>?> {
        return repository.buscar()
    }
}