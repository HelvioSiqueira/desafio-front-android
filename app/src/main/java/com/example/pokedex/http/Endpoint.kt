package com.example.pokedex.http

import com.example.pokedex.PokeListTypes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {

    @GET("pokemon-species?limit=100000&offset=0")
    suspend fun getPokeList(): Response<PokeListGson>

    @GET("pokemon/{idOrName}")
    suspend fun getPokemon(@Path("idOrName") idOrName: String): Response<PokemonJson>

    @GET("type/")
    suspend fun getPokeTypes(): Response<ListTypesGson>

    @GET("{url}")
    suspend fun getPokeListTypes(@Path("url") url: String): Response<PokeListTypes>
}