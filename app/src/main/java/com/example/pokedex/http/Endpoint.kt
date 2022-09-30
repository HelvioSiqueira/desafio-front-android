package com.example.pokedex.http

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {

    @GET("pokemon-species?limit=100000&offset=0")
    fun getPokeList(): Call<PokeListGson>

    @GET("/pokemon/{idOrName}")
    fun getPokemon(@Path("idOrName") idOrName: String): Call<PokemonJson>
}