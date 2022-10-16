package com.example.pokedex.repository

import androidx.lifecycle.LiveData
import com.example.pokedex.PokeList
import com.example.pokedex.Pokemon

interface PokedexRepository {

    fun salvar(pokemon: PokeList)
    fun excluir(vararg pokemonExcluidos: PokeList)
    fun buscar(): LiveData<List<PokeList>?>
    fun existeNaPokedex(id: Int): Int
}