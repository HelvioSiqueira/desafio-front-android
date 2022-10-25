package com.example.pokedex.adapter

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.model.PokeList
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemTypeBinding

class TypesRecycler(
    private val context: Context,
    private val typeList: List<PokeList>,
    private val callback: (PokeList) -> Unit
) : RecyclerView.Adapter<TypesRecycler.VH>() {

    private lateinit var binding: ItemTypeBinding

    private val icones: TypedArray by lazy {
        context.resources.obtainTypedArray(R.array.arr)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        binding = ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val vh = VH(binding)

        vh.itemView.setOnClickListener {
            val itemType = typeList[vh.adapterPosition]
            callback(itemType)
        }

        return vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val (type, _, _, _) = typeList[position]

        holder.binding.imgType.setImageDrawable(icones.getDrawable(position))

        holder.binding.txtType.text = type
    }

    override fun getItemCount() = typeList.size

    inner class VH(val binding: ItemTypeBinding) : RecyclerView.ViewHolder(binding.root)
}