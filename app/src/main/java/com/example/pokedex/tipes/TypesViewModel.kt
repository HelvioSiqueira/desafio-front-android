package com.example.pokedex.tipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.PokeList
import com.example.pokedex.repository.PokedexRepository
import com.example.pokedex.repository.http.model.TypesHttpUtils
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent

class TypesViewModel(private val repository: PokedexRepository) : ViewModel(), KoinComponent{

    private val typesHttp: TypesHttpUtils by inject()

    private val listTypes = mutableListOf<PokeList>()

    val onListIsReady = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
             listTypes.addAll(typesHttp.getPokeTypes())

            onListIsReady.value = true
        }
    }

    fun getListTypes() = listTypes
}