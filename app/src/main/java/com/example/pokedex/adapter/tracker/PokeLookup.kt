package com.example.pokedex.adapter.tracker

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.adapter.PokedexRecycler
import com.example.pokedex.adapter.TesteRecycler

class PokeLookup(val recyclerView: RecyclerView): ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {

        val view = recyclerView.findChildViewUnder(event.x, event.y)

        if(view != null){
            val holder = recyclerView.getChildViewHolder(view)

            return (holder as TesteRecycler.VH).pokeDetails
        }

        return null
    }
}