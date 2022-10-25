package com.example.pokedex.tipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.model.PokeList
import com.example.pokedex.adapter.PokedexRecycler
import com.example.pokedex.databinding.FragmentHomeBinding
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
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        arguments?.run {
            pokeUrl = getString("urlType").toString().substringAfterLast("v2/")
        }

        Log.d("HSV", "No ListPokeFragment: $pokeUrl")

        pokeList = viewModel.getPokeList(pokeUrl)

        viewModel.onListIsReady.observe(viewLifecycleOwner, Observer {
            if (it) {
                showPokeList(pokeList)
            }
        })

        return binding.root
    }

    //
    private fun onListItemClick(itemList: PokeList) {
        Log.d("HSV", "$activity")

        if (activity is OnPokemonClickListener) {
            val listener = activity as OnPokemonClickListener
            listener.onPokemonClick(itemList.name)
        }
    }

    private fun showPokeList(pokeList: List<PokeList>) {
        pokeListAdapter = PokedexRecycler(requireContext(), pokeList, this::onListItemClick)

        binding.rv.adapter = pokeListAdapter

        binding.rv.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    //Listener para abrir a lista de pokemon por tipos
    //(Precisou ser criada pois agora o a activity desse fagment é TypeListActivity)
    interface OnPokemonClickListener {
        fun onPokemonClick(poke_name: String)
    }

    companion object {
        const val TAG_TYPE_LIST = "tagType"
        private const val EXTRA_TYPE = "urlType"

        fun newInstance(urlType: String) = ListPokeFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_TYPE, urlType)
            }
        }
    }
}