package com.example.pokedex.adapter.tracker

import androidx.recyclerview.selection.ItemKeyProvider
import com.example.pokedex.model.PokeList

class PokeKeyProvider(private val pokeList: List<PokeList>) :
    ItemKeyProvider<Long>(ItemKeyProvider.SCOPE_MAPPED) {

    override fun getKey(position: Int) = pokeList[position].id.toLong()

    override fun getPosition(key: Long) = pokeList.indexOf(pokeList.single { it.id.toLong() == key })
}