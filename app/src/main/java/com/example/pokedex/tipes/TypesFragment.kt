package com.example.pokedex.tipes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import com.example.pokedex.PokeList
import com.example.pokedex.adapter.PokedexAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TypesFragment: ListFragment() {

    private lateinit var pokeTypesAdapter: PokedexAdapter
    private val viewModel: TypesViewModel by viewModel()

    private var listTypes = mutableListOf<PokeList>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onListIsReady.observe(viewLifecycleOwner, Observer {
            if(it){
                obterListTypes()
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("HSV", "TipesFragment startado")
    }

    private fun obterListTypes(){
        listTypes = viewModel.getListTypes()

        showListTypes(listTypes)
    }

    private fun showListTypes(listTypes: List<PokeList>){
        pokeTypesAdapter = PokedexAdapter(requireContext(), listTypes)
        val adapter = pokeTypesAdapter
        listAdapter = adapter
    }

}