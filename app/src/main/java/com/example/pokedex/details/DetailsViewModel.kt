package com.example.pokedex.details


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.Pokemon
import com.example.pokedex.http.PokemonJson
import com.example.pokedex.repository.PokeRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsViewModel(private val repository: PokeRepository) : ViewModel() {

    lateinit var pokemon: Pokemon
    val error = MutableLiveData<Boolean>()

    suspend fun getPokemon(pokeName: String): MutableLiveData<Pokemon> {

       return getPokeData(pokeName)
    }


    private suspend fun getPokeData(nomePoke: String): MutableLiveData<Pokemon> {
        val response = repository.pokeData(nomePoke)

        return toPokemon(response)
    }

    private fun toPokemon(response: Response<PokemonJson>): MutableLiveData<Pokemon> {
        pokemon = Pokemon()

        pokemon.apply {
            response.body()?.apply {
                name = _name
                id = _id
                height = _height
                weight = _weight
                types = _types.map { it.type.nameType }
                stats = _stats.map { mapOf(Pair(it.stat.nameStat, it.base_stat)) }
                abilites = _abilities.map { it.ability.nameAbility }
                sprite = _sprites.officilArtworK.frontDefault.url
            }
        }

        return MutableLiveData(pokemon)
    }
}