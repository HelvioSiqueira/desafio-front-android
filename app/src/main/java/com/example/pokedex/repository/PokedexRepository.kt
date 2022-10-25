package com.example.pokedex.repository

import androidx.lifecycle.LiveData
import com.example.pokedex.model.PokeList

interface PokedexRepository {

    fun salvar(pokemon: PokeList)
    fun excluir(vararg pokemonExcluidos: PokeList)
    fun buscar(): LiveData<List<PokeList>?>
    fun existeNaPokedex(id: Int): Int
}