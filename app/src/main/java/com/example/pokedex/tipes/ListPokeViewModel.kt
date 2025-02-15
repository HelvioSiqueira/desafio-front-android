package com.example.pokedex.tipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.PokeList
import com.example.pokedex.repository.PokedexRepository
import com.example.pokedex.repository.http.TypesHttpUtils
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent

class ListPokeViewModel(private val repository: PokedexRepository) : ViewModel(), KoinComponent {

    private val typesHttp: TypesHttpUtils by inject()

    private val pokeListTypes = mutableListOf<PokeList>()

    val onListIsReady = MutableLiveData<Boolean>()

    fun getPokeList(url: String): MutableList<PokeList> {

        viewModelScope.launch {
            pokeListTypes.addAll(typesHttp.getPokeListTypesApi(url))

            onListIsReady.value = true
        }

        return pokeListTypes
    }


}