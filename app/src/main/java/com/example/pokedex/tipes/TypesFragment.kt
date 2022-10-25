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
    ): View {

        binding = FragmentTipesBinding.inflate(layoutInflater)

        viewModel.onListIsReady.observe(viewLifecycleOwner, Observer {
            if (it) {
                obterListTypes()
            }
        })

        return binding.root
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

    //Se activity base for OnTypeClickListener executa o onTypeClick() que está
    //implementado na MainActivity, dessa forma a lista de pokemon por tipo já iria abrir normalmente
    //Mas a tala de detalhes não iria ser exibida pois agora a activity base do ListPokeFragment é TypeListActivity
    private fun onListItemClick(itemType: PokeList) {
        if (activity is OnTypeClickListener) {
            val listener = activity as OnTypeClickListener

            Log.d("HSV", "TypesFragment: ${itemType.url}")

            listener.onTypeClick(itemType.url)
        }
    }

    //Listener para abrir a lista de tipos
    interface OnTypeClickListener {
        fun onTypeClick(typeUrl: String)
    }
}