package com.example.pokedex.repository.room

import androidx.lifecycle.LiveData
import com.example.pokedex.Pokemon
import com.example.pokedex.repository.PokedexRepository

class RoomRepository(database: PokeDatabase): PokedexRepository {

    private val pokedexDao = database.pokeDao()

    override fun salvar(pokemon: Pokemon) {
        pokedexDao.insert(pokemon)
    }

    override fun excluir(vararg pokemonExcluidos: List<Pokemon>) {
        pokedexDao.delete(*pokemonExcluidos)
    }

    override fun buscar(): LiveData<List<Pokemon>> {
        return pokedexDao.search()
    }

    override fun existeNaPokedex(id: Int): Int {
        return pokedexDao.pokemonById(id)
    }


}