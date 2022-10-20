package com.example.pokedex.repository.http

import com.example.pokedex.PokeList
import com.example.pokedex.repository.http.model.PokeListGson
import com.example.pokedex.util.URL_IMG
import retrofit2.Response

class HomeHttpUtils(private val api: Endpoint) {

    private val pokeList = mutableListOf<PokeList>()

    suspend fun getPokeList(): MutableList<PokeList> {
        val response = api.getPokeList()

        if (response.isSuccessful) {
            toPokeList(response)
        }

        return pokeList
    }

    private fun toPokeList(response: Response<PokeListGson>) {
        response.body()?.results?.forEach {
            val poke = PokeList()

            poke.name = it.name
            poke.url = it.url
            poke.id = poke.url.substringAfterLast("species/").substringBeforeLast("/")
            poke.urlImg = "$URL_IMG${poke.id}.png"

            pokeList.add(poke)
        }
    }
}