package com.example.pokedex.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.PokeList
import com.example.pokedex.Pokemon
import com.example.pokedex.repository.PokedexRepository
import com.example.pokedex.repository.http.DetailsHttpUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailsViewModel(
    private val repository: PokedexRepository
) : ViewModel(), KoinComponent{

    lateinit var pokemon: Pokemon
    val error = MutableLiveData<Boolean>()

    var fillComplete = MutableLiveData<Boolean>()

    fun salvarNaPokedex(pokeList: PokeList) {
        repository.salvar(pokeList)
    }

    fun checar(id: Int): Int {
        return repository.existeNaPokedex(id)
    }

    suspend fun getPokemon(pokeName: String): MutableLiveData<Pokemon> {

        val pokeHttp: DetailsHttpUtils by inject()

        return pokeHttp.getPokemon(pokeName)
    }
}