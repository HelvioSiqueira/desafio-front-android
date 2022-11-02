package com.example.pokedex.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

                selectItem(vh, it, 2)

                callbackBool(true)

                true
            }
        }

        vh.itemView.setOnClickListener {
            if (!inDeleteMode) {
                val itemList = pokeList[vh.adapterPosition]

                callback(itemList)

                Log.d("HSV", "${it.isActivated}")

            } else {
                if (it.isActivated) {

                    selectItem(vh, it, 1)
                } else {

                    selectItem(vh, it, 2)
                }
            }
        }

        return vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val (id, name, _, urlImg) = pokeList[position]

        Glide.with(context).load(urlImg).into(holder.binding.imgView)

        holder.binding.txtNomeId.text = "N°$id $name"
    }

    override fun getItemCount() = pokeList.size

    inner class VH(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

    private fun selectItem(vh: VH, view: View, op: Int) {

        val itemList = pokeList[vh.adapterPosition]

        when (op) {
            1 -> {
                vh.binding.cardView.setCardBackgroundColor(Color.parseColor("#EBEBEB"))
                deletionList.remove(itemList)
                view.isActivated = false
            }
            2 -> {
                vh.binding.cardView.setCardBackgroundColor(Color.parseColor("#ff3f4b"))
                deletionList.add(itemList)
                view.isActivated = true
            }
        }
    }

    companion object {
        val deletionList = mutableListOf<PokeList>()

        fun clearDeletionList() {
            deletionList.clear()
        }
    }

}