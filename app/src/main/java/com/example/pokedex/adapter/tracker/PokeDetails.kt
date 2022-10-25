package com.example.pokedex.adapter.tracker

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import com.example.pokedex.model.PokeList

class PokeDetails(var pokeList: PokeList? = null, var adapterPosition: Int = -1) :
    ItemDetailsLookup.ItemDetails<Long>() {

    override fun getPosition() = adapterPosition

    override fun getSelectionKey() = pokeList!!.id.toLong()

    override fun inSelectionHotspot(e: MotionEvent) = true
}