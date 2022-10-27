package com.example.pokedex.repository.http

import android.util.Log
import com.example.pokedex.model.PokeList
import com.example.pokedex.util.URL_IMG

class TypesHttpUtils(private val api: Endpoint) {

    //Obtem a lista de pokemon por tipos
    suspend fun getPokeListTypesApi(url: String): MutableList<PokeList> {

        val pokeListTypes = mutableListOf<PokeList>()

        val response = api.getPokeListTypes(url)

        if (response.isSuccessful) {
            response.body()?.pokeListTypes?.forEach {
                val pokeList = PokeList()

                pokeList.url = it.pokemon.url
                pokeList.id = pokeList.url.substringAfterLast("pokemon/").substringBeforeLast("/")

                //A lista tem alguns pokemon com id de 10000 pra cima
                //que não são interessantes para puxar(Gigantamax, ex evolution)
                if (pokeList.id.toInt() < 10000) {
                    pokeList.name = it.pokemon.name
                    pokeList.urlImg = "$URL_IMG${pokeList.id}.png"

                    pokeListTypes.add(pokeList)
                }
            }
        }

        Log.d("HSV", pokeListTypes.toString())

        return pokeListTypes
    }

    suspend fun getPokeTypes(): MutableList<PokeList> {

        val listTypes = mutableListOf<PokeList>()

        val response = api.getPokeTypes()

        if (response.isSuccessful) {
            response.body()?.listTypes?.forEach {

                if (it.name != "unknown" && it.name != "shadow") {
                    val pokeList = PokeList()

                    pokeList.name = it.name
                    pokeList.url = it.url

                    listTypes.add(pokeList)
                }
            }
        }

        return listTypes
    }
}