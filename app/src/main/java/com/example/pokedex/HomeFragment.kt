package com.example.pokedex

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Lifecycle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : ListFragment(), MenuProvider, SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {

    val pokeList = mutableListOf<PokeList>()
    private lateinit var pokedexAdapter: PokedexAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        getData()

        showPokeList(pokeList)
    }

    private fun showPokeList(pokeList: List<PokeList>) {

        pokedexAdapter = PokedexAdapter(requireContext(), pokeList)

        val adapter = pokedexAdapter
        listAdapter = adapter
    }

    private fun getData() {
        val retrofitClient = NetworkUtils.getRetrofitInstace("https://pokeapi.co/api/v2/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        val callback = endpoint.getPokeList()

        callback.enqueue(object : Callback<PokeListGson> {
            override fun onFailure(call: Call<PokeListGson>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<PokeListGson>, response: Response<PokeListGson>) {

                response.body()?.results?.forEach {
                    val poke = PokeList()
                    poke.name = it.name
                    poke.url = it.url

                    pokeList.add(poke)
                }
                pokedexAdapter.notifyDataSetChanged()
            }
        })

        Log.d("HSV", "Acabou")
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.pokedex, menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem): Boolean {

        return true
    }

    override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {

        return true
    }
}