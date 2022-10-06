package com.example.pokedex.details


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.Pokemon
import com.example.pokedex.http.PokemonJson
import com.example.pokedex.repository.PokeRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(private val repository: PokeRepository): ViewModel() {

    lateinit var pokemon: Pokemon
    val error = MutableLiveData<Boolean>()

    private suspend fun getPokeData(nomePoke: String): Pokemon{
        val callback = repository.pokeData(nomePoke)

        callback.enqueue(object : Callback<PokemonJson> {
            override fun onFailure(call: Call<PokemonJson>, t: Throwable) {
                error.value = true
            }

            override fun onResponse(call: Call<PokemonJson>, response: Response<PokemonJson>) {
                pokemon = toPokemon(response.body())
            }
        })

        return pokemon
    }

    private fun toPokemon(pokemonJson: PokemonJson?): Pokemon {
        val pokemon = Pokemon()

        pokemon.apply {
            name = pokemonJson!!._name
            id = pokemonJson._id
            height = pokemonJson._height
            weight = pokemonJson._weight
            types = pokemonJson._types.map { it.type.nameType }
            stats = pokemonJson.stats.map { mapOf(Pair(it.stat.nameStat, it.base_stat)) }
            abilites = pokemonJson.abilities.map { it.ability.nameAbility }
            sprite = pokemonJson.sprites.officilArtworK.frontDefault.url
        }

        return pokemon
    }
}