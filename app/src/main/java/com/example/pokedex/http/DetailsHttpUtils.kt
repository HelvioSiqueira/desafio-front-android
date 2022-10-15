package com.example.pokedex.http

import com.example.pokedex.http.model.EvolvesToFirst
import com.example.pokedex.http.model.EvolvesToSecond
import com.example.pokedex.http.model.PokeEvolutionChain
import retrofit2.Response

object DetailsHttpUtils {

    //Obtem a corrente de evolução do pokemon
    fun getEvolutions(responsePokeEvolution: Response<PokeEvolutionChain>): MutableList<MutableList<String>> {

        val allEvolutions = mutableListOf<MutableList<String>>()

        //Obtem a forma inicial do pokemon
        var initial = ""

        responsePokeEvolution.body()?.chain?.species?.run {
            initial = name
            initial += "|${url.substringAfterLast("pokemon-species/").substringBeforeLast("/")}"
        }

        allEvolutions.add(mutableListOf(initial!!))

        val evolutionFirst = mutableListOf<String>()
        val evolutionSecond = mutableListOf<String>()

        if (responsePokeEvolution.isSuccessful) {
            responsePokeEvolution.body()?.chain?.evolvesToFirst?.forEach { it_first ->

                evolutionFirst.add(getFirstEvolution(it_first))

                it_first.evolvesToSecond?.forEach { it_second ->
                    evolutionSecond.add(getSecondEvolution(it_second))
                }
            }
        }

        allEvolutions.add(evolutionFirst)
        allEvolutions.add(evolutionSecond)

        return allEvolutions
    }

    //Obtem a lista das primeiras evoluções(se tiver)
    private fun getFirstEvolution(first: EvolvesToFirst?): String {

        var name = ""

        if (first != null) {
            name = first.species.name

            name += "|${
                first.species.url.substringAfterLast("pokemon-species/").substringBeforeLast("/")
            }"
        }

        return name
    }

    //Obtem a lista das segundas evoluções(se tiver)
    private fun getSecondEvolution(second: EvolvesToSecond?): String {

        var name = ""

        if (second != null) {
            name = second.species.name

            name += "|${
                second.species.url.substringAfterLast("pokemon-species/").substringBeforeLast("/")
            }"
        }

        return name
    }
}