package com.example.pokedex.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.pokedex.PokeList
import com.example.pokedex.R
import com.example.pokedex.adapter.PokedexAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : ListFragment(), MenuProvider, SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener{

    private val viewModel: HomeViewModel by viewModel()

    var pokeList = mutableListOf<PokeList>()
    private lateinit var pokedexAdapter: PokedexAdapter

    private var searchView: SearchView? = null
    private var lastSearchTerm: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.onListIsReady.observe(viewLifecycleOwner, Observer { ready->
            if(ready){
                obterLista()
            }
        })
    }

    private fun obterLista(){
        viewModel.getList().observe(viewLifecycleOwner, Observer { list->
            showPokeList(list)
        })

        viewModel.searchTerm.value = ""
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val item = l.getItemAtPosition(position) as PokeList

        val args = Bundle().apply {
            putString("poke_name", item.name)
        }

        Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            .navigate(R.id.action_homeFragment_to_detailsFragment, args)

    }

    private fun showPokeList(pokeList: List<PokeList>) {

        pokedexAdapter = PokedexAdapter(requireContext(), pokeList)

        val adapter = pokedexAdapter
        listAdapter = adapter

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