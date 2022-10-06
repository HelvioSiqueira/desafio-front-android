package com.example.pokedex.home

import android.util.Log
import androidx.lifecycle.*
import com.example.pokedex.PokeList
import com.example.pokedex.http.PokeListGson
import com.example.pokedex.repository.PokeRepository
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
            Log.d("HSV", "No init: ${pokeList.joinToString(" | ")}")
        }
    }

    fun getList(): LiveData<List<PokeList>> = Transformations.switchMap(searchTerm) { term ->
        MutableLiveData(pokeList.filter { it.name.uppercase().contains(term.uppercase()) })
    }

    private suspend fun getPokeList() {
        val response = repository.pokeList()

        if(response.isSuccessful){
            toPokeList(response)
        }
    }

    private fun toPokeList(response: Response<PokeListGson>) {
        response.body()?.results?.forEach {
            val poke = PokeList()

            poke.name = it.name
            poke.url = it.url

            pokeList.add(poke)
        }
        if(response.isSuccessful){
            Log.d("HSV", "No getList : ${pokeList.joinToString(" | ")}")
            onListIsReady.value = true
        }


    }
}