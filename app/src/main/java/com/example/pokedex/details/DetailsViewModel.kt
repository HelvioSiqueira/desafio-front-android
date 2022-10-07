package com.example.pokedex.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.Pokemon
import com.example.pokedex.http.model.*
import com.example.pokedex.repository.PokeRepository
import retrofit2.Response

class DetailsViewModel(private val repository: PokeRepository) : ViewModel() {

    lateinit var pokemon: Pokemon
    val error = MutableLiveData<Boolean>()

    //Passa os dois responses para toPokemon
    //O responseGetPokeData() obtem o detalhes do pokemon no https://pokeapi.co/api/v2/pokemon/
    //O responsePokeEvolution() obtem a url(evolution_chain) e evolução anterior(evolves_from_species)
    //no https://pokeapi.co/api/v2/pokemon-species/
    suspend fun getPokemon(pokeName: String): MutableLiveData<Pokemon> {
        val responseGetPokeData = getPokeData(pokeName)
        val responsePokeEvolution = getPokeEvolution(pokeName)

        return toPokemon(responseGetPokeData, responsePokeEvolution)
    }

    private suspend fun getPokeData(nomePoke: String): Response<PokemonGson> {
        return repository.pokeData(nomePoke)
    }

    private suspend fun getPokeEvolution(nomeSpecie: String): Response<PokeEvolution> {
        return repository.pokeEvolution(nomeSpecie)
    }

    //Devolve a corrente de evolução para ser usada no toPokemon()
    private suspend fun getPokeEvolutionChain(url: String): MutableList<MutableList<String>> {

        val response = repository.pokeEvolutionChain(url)

        return getEvolutions(response)
    }

    //Obtem a corrente de evolução do pokemon
    private fun getEvolutions(responsePokeEvolution: Response<PokeEvolutionChain>): MutableList<MutableList<String>> {

        val allEvolutions = mutableListOf<MutableList<String>>()

        //Obtem a forma inicial do pokemon
        val initial = responsePokeEvolution.body()?.chain?.species?.name

        allEvolutions.add(mutableListOf(initial!!))

        val evolutionFirst = mutableListOf<String>()
        val evolutionSecond = mutableListOf<String>()

        if(responsePokeEvolution.isSuccessful){
            responsePokeEvolution.body()?.chain?.evolvesToFirst?.forEach { it_first->

                evolutionFirst.add(getFirstEvolution(it_first))

                it_first.evolvesToSecond?.forEach { it_second->
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

        if(first != null){
            name = first.species.name
        }

        return name
    }

    //Obtem a lista das segundas evoluções(se tiver)
    private fun getSecondEvolution(second: EvolvesToSecond?): String {

        var name = ""

        if(second != null){
            name = second.species.name
        }

        return name
    }

    private suspend fun toPokemon(
        responsePokemonGson: Response<PokemonGson>, responsePokeEvolution: Response<PokeEvolution>
    ): MutableLiveData<Pokemon> {
        pokemon = Pokemon()

        var urlEvolutionChain = ""

        pokemon.apply {
            responsePokemonGson.body()?.apply {
                name = _name
                id = _id
                height = _height
                weight = _weight
                types = _types.map { it.type.nameType }
                stats = _stats.map { mapOf(Pair(it.stat.nameStat, it.base_stat)) }
                abilites = _abilities.map { it.ability.nameAbility }
                sprite = _sprites.officilArtworK.frontDefault.url
            }

            responsePokeEvolution.body()?.apply {
                evolvesFrom = _evolvesFrom?.name
                urlEvolutionChain = _evolutionChain.url
            }

            evolutionChain = getPokeEvolutionChain(urlEvolutionChain.substringAfterLast("v2/"))
        }
        return MutableLiveData(pokemon)
    }
}