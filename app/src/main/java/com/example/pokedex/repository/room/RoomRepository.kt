package com.example.pokedex.repository.room

import androidx.lifecycle.LiveData
import com.example.pokedex.PokeList
import com.example.pokedex.Pokemon
import com.example.pokedex.repository.PokedexRepository

class RoomRepository(database: PokeDatabase): PokedexRepository {
    private val pokedexDao = database.pokeDao()

    override fun salvar(pokemon: PokeList) {
        pokedexDao.insert(pokemon)
    }

    override fun excluir(vararg pokemonExcluidos: PokeList) {
        pokedexDao.delete(*pokemonExcluidos)
    }

    override fun buscar(): LiveData<List<PokeList>?> {
        return pokedexDao.search()
    }

    override fun existeNaPokedex(id: Int): Int {
        return pokedexDao.pokemonById(id)
    }

}