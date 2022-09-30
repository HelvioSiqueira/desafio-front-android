package com.example.pokedex

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pokedex.databinding.ItemPokemonBinding

class PokedexAdapter(context: Context, pokeList: List<PokeList>): ArrayAdapter<PokeList>(context, 0, pokeList) {

    private lateinit var binding: ItemPokemonBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val poke = getItem(position)

        Log.d("HSV", poke!!.name)

        binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = if(convertView == null){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
            val holder = VH(binding)
            view.tag = holder
            holder
        } else {
            convertView.tag as VH
        }

        viewHolder.textName.text = poke.name.replaceFirstChar { it.uppercase() }
        return viewHolder.binding.root
    }

    class VH(val binding: ItemPokemonBinding){
        val textName: TextView = binding.txtNomePokemon
    }
}