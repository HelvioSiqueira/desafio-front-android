package com.example.pokedex.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.model.PokeList
import com.example.pokedex.adapter.tracker.PokeDetails
import com.example.pokedex.databinding.ItemPokemonBinding

class TesteRecycler(
    private val context: Context,
    private val pokeList: List<PokeList>,
    private val callback: (PokeList) -> Unit
) : RecyclerView.Adapter<TesteRecycler.VH>() {

    private lateinit var binding: ItemPokemonBinding

    lateinit var selectionTracker: SelectionTracker<Long>

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

        holder.pokeDetails.pokeList = pokeList[position]
        holder.pokeDetails.adapterPosition = position

        if (selectionTracker.isSelected(holder.pokeDetails.selectionKey)) {
            holder.itemView.setBackgroundColor(Color.RED)
            holder.itemView.isActivated = true
        } else {
            holder.itemView.isActivated = false
        }
    }

    override fun getItemCount() = pokeList.size

    inner class VH(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {

        val pokeDetails: PokeDetails = PokeDetails()

    }
}