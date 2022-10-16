package com.example.pokedex.repository

import androidx.lifecycle.LiveData
import com.example.pokedex.Pokemon

interface PokedexRepository {

    fun salvar(pokemon: Pokemon)
    fun excluir(vararg pokemonExcluidos: List<Pokemon>)
    fun buscar(): LiveData<List<Pokemon>>
    fun existeNaPokedex(id: Int): Int
}