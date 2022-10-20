package com.example.pokedex.home

import androidx.lifecycle.*
import com.example.pokedex.PokeList
import com.example.pokedex.repository.PokedexRepository
import com.example.pokedex.repository.http.HomeHttpUtils
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent

class HomeViewModel(private val repository: PokedexRepository) : ViewModel(), KoinComponent {

    private val pokeHttp: HomeHttpUtils by inject()

    private val pokeList = mutableListOf<PokeList>()

    val onListIsReady = MutableLiveData<Boolean>()

    val searchTerm = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            pokeList.addAll(pokeHttp.getPokeList())

            onListIsReady.value = true
        }
    }

    fun getList(): LiveData<List<PokeList>> = Transformations.switchMap(searchTerm) { term ->
        MutableLiveData(pokeList.filter { it.name.uppercase().contains(term.uppercase()) })
    }
}