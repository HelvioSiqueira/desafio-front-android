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

    //Precisa que a classe implemente KoinComponent para usar o by inject() em uma classe que não
    //seja um Activity, Fragment ou Service
    private val pokeHttp: DetailsHttpUtils by inject()

    lateinit var pokemon: Pokemon

    var ocultar = MutableLiveData<Boolean>()

    var fillComplete = MutableLiveData<Boolean>()

    fun salvarNaPokedex(pokeList: PokeList) {
        repository.salvar(pokeList)
    }

    fun checar(id: Int): Int {
        return repository.existeNaPokedex(id)
    }

    suspend fun getPokemon(pokeName: String): Pokemon {
        return pokeHttp.getPokemon(pokeName)
    }
}