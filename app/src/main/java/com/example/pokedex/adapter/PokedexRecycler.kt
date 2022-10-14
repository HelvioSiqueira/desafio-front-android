package com.example.pokedex.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.MainActivity
import com.example.pokedex.PokeList
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.databinding.ItemPokemonBinding
import com.example.pokedex.home.HomeFragment

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

        Glide.with(context).load(urlImg).into(holder.binding.imgView)

        holder.binding.txtNomeId.text = "N°$id $name"
    }

    override fun getItemCount() = pokeList.size

    inner class VH(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)
}

class Scroll(): RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        when (newState) {
            RecyclerView.SCROLL_STATE_IDLE -> Log.d("HSV", "Sem scroll")
            RecyclerView.SCROLL_STATE_DRAGGING -> {
                Log.d("HSV", "Scrollando")
                //MainActivity().onListScrolled(false)
            }
            RecyclerView.SCROLL_STATE_SETTLING -> Log.d("HSV", "Scrool configurado")
        }

    }
}
