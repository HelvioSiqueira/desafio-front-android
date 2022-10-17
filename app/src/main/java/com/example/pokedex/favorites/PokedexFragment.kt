package com.example.pokedex.favorites

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.PokeList
import com.example.pokedex.R
import com.example.pokedex.adapter.PokedexRecycler
import com.example.pokedex.databinding.FragmentPokedexBinding
import com.example.pokedex.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

//Excluir HttpRepository quando deixar de ser usada
class PokedexFragment: Fragment() {

    private lateinit var binding: FragmentPokedexBinding

    var pokeList = mutableListOf<PokeList>()
    private lateinit var pokedexAdapter: PokedexRecycler

    private val viewModel: PokedexViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokedexBinding.inflate(layoutInflater)

        viewModel.getListFav().observe(viewLifecycleOwner, Observer {
            if(it != null){
                showPokeList(it)
            }
        })

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("HSV", "PokedexFragment startado")
    }

    private fun showPokeList(pokeList: List<PokeList>) {
        pokedexAdapter = PokedexRecycler(requireContext(), pokeList, this::onListItemClick)

        binding.rvFav.adapter = pokedexAdapter

        val layoutManager = GridLayoutManager(requireContext(), 2)

        binding.rvFav.layoutManager = layoutManager
    }

    private fun onListItemClick(itemLista: PokeList) {

        if(activity is HomeFragment.OnPokemonClickListener){
            val listener = activity as HomeFragment.OnPokemonClickListener
            listener.onPokemonClick(itemLista.name)
        }

    }
}