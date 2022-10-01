package com.example.pokedex

import com.example.pokedex.http.PokeListGson
import android.os.Bundle
import android.os.Handler
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

    private var searchView: SearchView? = null
    private var lastSearchTerm: String = ""

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

    private fun getPokeData() {
        val callback = endpoint.getPokemon("1")

        callback.enqueue(object : Callback<PokemonJson> {
            override fun onFailure(call: Call<PokemonJson>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<PokemonJson>, response: Response<PokemonJson>) {
                val pokemon = response.body()

                Log.d("HSV", toPokemon(pokemon).toString())

                Log.d("HSV", pokemon.toString())

            }
        })
    }

    private fun toPokemon(pokemonJson: PokemonJson?): Pokemon {
        val pokemon = Pokemon()

        pokemon.apply {
            name = pokemonJson!!._name
            id = pokemonJson._id
            height = pokemonJson._height
            weight = pokemonJson._weight
            types = pokemonJson._types.map { it.type.nameType }
            stats = pokemonJson.stats.map { mapOf(Pair(it.stat.nameStat, it.base_stat)) }
            abilites = pokemonJson.abilities.map { it.ability.nameAbility }
            sprite = pokemonJson.sprites.officilArtworK.frontDefault.url
        }

        return pokemon
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
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

    companion object {
        const val API = "https://pokeapi.co/api/v2/"
    }
}