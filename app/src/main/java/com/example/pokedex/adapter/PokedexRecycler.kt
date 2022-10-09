package com.example.pokedex.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.PokeList
import com.example.pokedex.databinding.ItemPokemonBinding

class PokedexRecycler(
    private val context: Context,
    private val pokeList: List<PokeList>,
    private val callback: (PokeList) -> Unit
) : RecyclerView.Adapter<PokedexRecycler.VH>() {

    private lateinit var binding: ItemPokemonBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val vh = VH(binding)

        vh.itemView.setOnClickListener {
            val itemList = pokeList[vh.adapterPosition]
            callback(itemList)
        }

        return vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val (name, id, _, urlImg) = pokeList[position]

        Log.d("HSV", "${pokeList[position]}")

        Glide.with(context).load(urlImg).into(holder.binding.imgView)

        holder.binding.txtNomeId.text = "N°$id $name"
    }

    override fun getItemCount() = pokeList.size

    inner class VH(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)
}