package com.example.pokedex.repository.http

import com.example.pokedex.repository.http.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {

    //Obtem a lista de todos os pokemon
    @GET("pokemon-species?limit=100000&offset=0")
    suspend fun getPokeList(): Response<PokeListGson>

    //Obtem os detalhes pokemon pelo id ou nome
    @GET("pokemon/{idOrName}")
    suspend fun getPokemon(@Path("idOrName") idOrName: String): Response<PokemonGson>

    //Obtem a lista de tipos(normal, voador, fogo, etc...)
    @GET("type/")
    suspend fun getPokeTypes(): Response<ListTypesGson>

    //Obtem a lista de pokemon por tipo
    @GET("{url}")
    suspend fun getPokeListTypes(@Path("url") url: String): Response<PokeListTypes>

    //Obtem os detalhes pelo pokemon-species(api)
    @GET("pokemon-species/{specie}")
    suspend fun getPokeEvolution(@Path("specie") specie: String): Response<PokeEvolution>

    //Pega a corrente de evolução do pokemon(url obtida no getPokeEvolution)
    @GET("{url}")
    suspend fun getPokeEvolutionChain(@Path("url") url: String): Response<PokeEvolutionChain>
}