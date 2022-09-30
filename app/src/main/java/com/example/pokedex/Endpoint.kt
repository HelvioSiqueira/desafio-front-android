package com.example.pokedex

import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {

    @GET("pokemon-species?limit=100000&offset=0")
    fun getPokeList(): Call<PokeListGson>
}