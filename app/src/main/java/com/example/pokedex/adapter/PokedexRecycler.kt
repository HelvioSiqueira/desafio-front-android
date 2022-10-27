package com.example.pokedex.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.model.PokeList
import com.example.pokedex.databinding.ItemPokemonBinding
import com.example.pokedex.favorites.PokedexFragment

class PokedexRecycler(
    private val context: Context,
    private val pokeList: List<PokeList>,
    private val callback: (PokeList) -> Unit,
    private val fragmentKey: String? = "",
    private val callbackBool: (Boolean) -> Unit = {},
) : RecyclerView.Adapter<PokedexRecycler.VH>() {

    private lateinit var binding: ItemPokemonBinding

    private var inDeleteMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val vh = VH(binding)

        if (fragmentKey == PokedexFragment.FRAGMENT_POKEDEX) {
            vh.itemView.setOnLongClickListener {

                inDeleteMode = true

                callbackBool(true)

                true
            }
        }

        vh.itemView.setOnClickListener {
            if(!inDeleteMode){
                val itemList = pokeList[vh.adapterPosition]
                callback(itemList)
                it.isClickable
            } else {
                vh.binding.cardView.setCardBackgroundColor(Color.parseColor("#ef6564"))
                val itemList = pokeList[vh.adapterPosition]

                deletionIndices.add(vh.adapterPosition)
                deletionList.add(itemList)
                Log.d("HSV", deletionList.joinToString(separator = ", "))
            }
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

    companion object{
        val deletionList = mutableListOf<PokeList>()
        val deletionIndices = mutableListOf<Int>()

        fun clearDeletionList(){
            deletionList.clear()
        }
    }

}