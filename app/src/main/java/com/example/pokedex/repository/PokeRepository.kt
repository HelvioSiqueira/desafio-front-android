package com.example.pokedex.repository

import com.example.pokedex.http.Endpoint

class PokeRepository(private val api: Endpoint) {

    suspend fun pokeList() = api.getPokeList()
    suspend fun pokeData(nomePoke: String) = api.getPokemon(nomePoke)
    suspend fun listTypes() = api.getPokeTypes()
    suspend fun pokeListType(url: String) = api.getPokeListTypes(url)

}