package com.example.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pokedex.model.PokeList
import com.example.pokedex.databinding.ItemPokemonBinding

class PokedexAdapter(context: Context, pokeList: List<PokeList>) :
    ArrayAdapter<PokeList>(context, 0, pokeList) {

    private lateinit var binding: ItemPokemonBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val poke = getItem(position)

        val viewHolder = if (convertView == null) {
            binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val holder = VH(binding)
            binding.root.tag = holder

            //Log.d("HSV", poke!!.name)

            holder
        } else {

            //Log.d("HSV", poke!!.name)

            convertView.tag as VH
        }

        viewHolder.textName.text = poke!!.name.replaceFirstChar { it.uppercase() }
        return viewHolder.binding.root
    }

    class VH(val binding: ItemPokemonBinding) {
        val textName: TextView = binding.txtNomeId
    }
}