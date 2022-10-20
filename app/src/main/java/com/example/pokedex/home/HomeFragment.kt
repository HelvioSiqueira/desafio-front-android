package com.example.pokedex.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.PokeList
import com.example.pokedex.R
import com.example.pokedex.adapter.PokedexRecycler
import com.example.pokedex.adapter.Scroll
import com.example.pokedex.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var binding: FragmentHomeBinding

    var pokeList = mutableListOf<PokeList>()
    private lateinit var pokedexAdapter: PokedexRecycler

    private var searchView: SearchView? = null
    private var lastSearchTerm: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.rv.addOnScrollListener(Scroll())

        Log.d("HSV", "Estado: ${binding.rv.scrollState}")

        Log.d("HSV", activity.toString())

        viewModel.onListIsReady.observe(viewLifecycleOwner, Observer { ready ->
            if (ready) {
                obterLista()
            }
        })

        return binding.root
    }

    private fun obterLista() {
        viewModel.getList().observe(viewLifecycleOwner, Observer { list ->
            showPokeList(list)
            pokeList = list.toMutableList()
        })

        viewModel.searchTerm.value = ""
    }

    private fun showPokeList(pokeList: List<PokeList>) {
        pokedexAdapter = PokedexRecycler(requireContext(), pokeList, this::onListItemClick)

        binding.rv.adapter = pokedexAdapter

        val layoutManager = GridLayoutManager(requireContext(), 2)

        binding.rv.layoutManager = layoutManager
    }

    private fun onListItemClick(itemLista: PokeList) {

        if(activity is OnPokemonClickListener){
            val listener = activity as OnPokemonClickListener
            listener.onPokemonClick(itemLista.name)
        }
    }

    interface OnPokemonClickListener{
        fun onPokemonClick(poke_name: String)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.pokedex, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchItem.setOnActionExpandListener(this)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = getString(R.string.hint_search)
        searchView?.setOnQueryTextListener(this)

        if (lastSearchTerm.isNotEmpty()) {
            Handler().post {
                val query = lastSearchTerm
                searchItem.expandActionView()
                searchView?.setQuery(query, true)
                searchView?.clearFocus()
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem) = true

    override fun onQueryTextSubmit(query: String?) = true

    override fun onQueryTextChange(newText: String?): Boolean {
        lastSearchTerm = newText ?: ""
        showPokeList(pokeList.filter { it.name.uppercase().contains(lastSearchTerm.uppercase()) })

        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem) = true

    override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
        lastSearchTerm = ""
        showPokeList(pokeList)

        return true
    }
}