package com.example.pokedex.home

import android.util.Log
import androidx.lifecycle.*
import com.example.pokedex.PokeList
import com.example.pokedex.http.model.PokeListGson
import com.example.pokedex.repository.PokeRepository
import com.example.pokedex.util.URL_IMG
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repository: PokeRepository) : ViewModel() {

    val error = MutableLiveData<Boolean>()

    private val pokeList = mutableListOf<PokeList>()

    val onListIsReady = MutableLiveData<Boolean>()

    val searchTerm = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            getPokeList()
        }
    }

    fun getList(): LiveData<List<PokeList>> = Transformations.switchMap(searchTerm) { term ->
        MutableLiveData(pokeList.filter { it.name.uppercase().contains(term.uppercase()) })
    }

    private suspend fun getPokeList() {
        val response = repository.pokeList()

        if (response.isSuccessful) {
            toPokeList(response)
        }
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
        if (response.isSuccessful) {
            onListIsReady.value = true
        }
    }
}