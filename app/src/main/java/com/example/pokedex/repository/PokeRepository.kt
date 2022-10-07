package com.example.pokedex.repository

import com.example.pokedex.http.Endpoint

class PokeRepository(private val api: Endpoint) {

    suspend fun pokeList() = api.getPokeList()
    suspend fun pokeData(nomePoke: String) = api.getPokemon(nomePoke)
    suspend fun listTypes() = api.getPokeTypes()
    suspend fun pokeListType(url: String) = api.getPokeListTypes(url)
    suspend fun pokeEvolution(specie: String) = api.getPokeEvolution(specie)
    suspend fun pokeEvolutionChain(url: String) = api.getPokeEvolutionChain(url)

}