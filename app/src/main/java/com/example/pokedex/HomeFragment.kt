package com.example.pokedex

import com.example.pokedex.http.PokeListGson
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Lifecycle
import com.example.pokedex.adapter.PokedexAdapter
import com.example.pokedex.http.Endpoint
import com.example.pokedex.http.NetworkUtils
import com.example.pokedex.http.PokemonJson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : ListFragment(), MenuProvider, SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {

    val pokeList = mutableListOf<PokeList>()
    private lateinit var pokedexAdapter: PokedexAdapter

    private val retrofitClient = NetworkUtils.getRetrofitInstace(API)
    private val endpoint = retrofitClient.create(Endpoint::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        getListPoke()
        getPokeData()

        showPokeList(pokeList)
    }

    private fun showPokeList(pokeList: List<PokeList>) {

        pokedexAdapter = PokedexAdapter(requireContext(), pokeList)

        val adapter = pokedexAdapter
        listAdapter = adapter
    }

    private fun getListPoke() {
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
    }

    private fun getPokeData(){
        val callback = endpoint.getPokemon("1")

        callback.enqueue(object: Callback<PokemonJson>{
            override fun onFailure(call: Call<PokemonJson>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<PokemonJson>, response: Response<PokemonJson>) {
                val pokemon = response.body()

                Log.d("HSV", pokemon.toString())
            }
        })
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

    companion object {
        const val API = "https://pokeapi.co/api/v2/"
    }
}