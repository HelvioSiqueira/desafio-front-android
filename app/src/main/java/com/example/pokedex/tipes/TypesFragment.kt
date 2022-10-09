package com.example.pokedex.tipes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.PokeList
import com.example.pokedex.R
import com.example.pokedex.adapter.PokedexAdapter
import com.example.pokedex.adapter.PokedexRecycler
import com.example.pokedex.adapter.TypesRecycler
import com.example.pokedex.databinding.FragmentTipesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TypesFragment : Fragment() {

    private lateinit var binding: FragmentTipesBinding

    private lateinit var pokeTypesAdapter: TypesRecycler
    private val viewModel: TypesViewModel by viewModel()

    private var listTypes = mutableListOf<PokeList>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTipesBinding.inflate(layoutInflater)

        viewModel.onListIsReady.observe(viewLifecycleOwner, Observer {
            if (it) {
                obterListTypes()
            }
        })

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("HSV", "TipesFragment startado")
    }

    private fun obterListTypes() {
        listTypes = viewModel.getListTypes()

        showListTypes(listTypes)
    }

    private fun showListTypes(listTypes: List<PokeList>) {
        pokeTypesAdapter = TypesRecycler(requireContext(), listTypes, this::onListItemClick)

        binding.rvTypes.adapter = pokeTypesAdapter

        binding.rvTypes.layoutManager = GridLayoutManager(requireContext(), 3)

    }

    fun onListItemClick(itemType: PokeList) {

        val args = Bundle().apply {
            putString("pokeUrl", itemType.url)
        }

        Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            .navigate(R.id.action_action_tipes_to_listPokeFragment, args)
    }

}