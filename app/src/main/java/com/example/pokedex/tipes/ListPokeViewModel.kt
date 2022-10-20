package com.example.pokedex.tipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.PokeList
import com.example.pokedex.repository.http.HttpRepository
import com.example.pokedex.repository.http.model.TypesHttpUtils
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent

class ListPokeViewModel(private val repository: HttpRepository) : ViewModel(), KoinComponent {

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