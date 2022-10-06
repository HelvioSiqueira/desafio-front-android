package com.example.pokedex.tipes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import com.example.pokedex.PokeList
import com.example.pokedex.adapter.PokedexAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListPokeFragment : ListFragment() {

    private var pokeUrl = ""

    private var pokeList = mutableListOf<PokeList>()

    private lateinit var pokeListAdapter: PokedexAdapter

    private val viewModel: ListPokeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.run {
            pokeUrl = getString("pokeUrl").toString().substringAfterLast("v2/")
        }

        pokeList = viewModel.getPokeList(pokeUrl)

        viewModel.onListIsReady.observe(viewLifecycleOwner, Observer {
            if(it){
                showPokeList(pokeList)
            }
        })
    }

    fun showPokeList(pokeList: List<PokeList>) {
        pokeListAdapter = PokedexAdapter(requireContext(), pokeList)
        val adapter = pokeListAdapter
        listAdapter = adapter
    }
}