package com.example.pokedex.tipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.PokeList
import com.example.pokedex.R
import com.example.pokedex.adapter.PokedexAdapter
import com.example.pokedex.adapter.PokedexRecycler
import com.example.pokedex.databinding.FragmentHomeBinding
import com.example.pokedex.databinding.ItemPokemonBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListPokeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private var pokeUrl = ""

    private var pokeList = mutableListOf<PokeList>()

    private lateinit var pokeListAdapter: PokedexRecycler

    private val viewModel: ListPokeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        arguments?.run {
            pokeUrl = getString("pokeUrl").toString().substringAfterLast("v2/")
        }

        pokeList = viewModel.getPokeList(pokeUrl)

        viewModel.onListIsReady.observe(viewLifecycleOwner, Observer {
            if (it) {
                showPokeList(pokeList)
            }
        })

        return binding.root
    }

    private fun onListItemClick(itemList: PokeList) {

        val args = Bundle().apply {
            putString("poke_name", itemList.name)
        }

        Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            .navigate(R.id.action_listPokeFragment_to_detailsFragment, args)

    }

    private fun showPokeList(pokeList: List<PokeList>) {
        pokeListAdapter = PokedexRecycler(requireContext(), pokeList, this::onListItemClick)

        binding.rv.adapter = pokeListAdapter

        binding.rv.layoutManager = GridLayoutManager(requireContext(), 2)
    }
}