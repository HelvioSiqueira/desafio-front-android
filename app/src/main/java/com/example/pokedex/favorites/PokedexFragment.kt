package com.example.pokedex.favorites

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.model.PokeList
import com.example.pokedex.adapter.PokedexRecycler
import com.example.pokedex.databinding.FragmentPokedexBinding
import com.example.pokedex.home.HomeFragment
import okhttp3.internal.notifyAll
import org.koin.androidx.viewmodel.ext.android.viewModel

//Excluir HttpRepository quando deixar de ser usada
class PokedexFragment : Fragment(), ActionMode.Callback{

    private lateinit var binding: FragmentPokedexBinding

    var pokeList = mutableListOf<PokeList>()
    private lateinit var pokedexAdapter: PokedexRecycler

    private val viewModel: PokedexViewModel by viewModel()

    private var actionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokedexBinding.inflate(layoutInflater)

        viewModel.getListFav().observe(viewLifecycleOwner, Observer {
            if (it != null) {

                pokeList.addAll(it)
                showPokeList()
            }
        })

        viewModel.inDeleteMode.observe(viewLifecycleOwner, Observer {
            if(it){
                showDeleteMode()
            }
        })
        return binding.root
    }

    private fun showPokeList() {
        pokedexAdapter = PokedexRecycler(requireContext(), pokeList, this::onListItemClick, FRAGMENT_POKEDEX, this::activeDeleteMode)

        binding.rvFav.adapter = pokedexAdapter

        val layoutManager = GridLayoutManager(requireContext(), 2)

        binding.rvFav.layoutManager = layoutManager
    }

    private fun showDeleteMode(){
        val appCompatActivity = (activity as AppCompatActivity)

        actionMode = appCompatActivity.startActionMode(this)
    }

    private fun activeDeleteMode(status: Boolean){
        viewModel.inDeleteMode.value = status
    }

    private fun onListItemClick(itemLista: PokeList) {
        if (activity is HomeFragment.OnPokemonClickListener) {
            val listener = activity as HomeFragment.OnPokemonClickListener
            listener.onPokemonClick(itemLista.name)
        }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        activity?.menuInflater?.inflate(R.menu.poke_delete_list, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

    override fun onActionItemClicked(mode: ActionMode?, menuItem: MenuItem?): Boolean {

        val deletionList = PokedexRecycler.deletionList

        Log.d("HSV", "Depois de excluida: ${pokeList.joinToString(", ")}")

        if(menuItem?.itemId == R.id.action_delete){
            pokeList.clear()
            viewModel.deletedSelection(*deletionList.toTypedArray())
            //activity?.recreate()
        }

        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
        activeDeleteMode(false)
        showPokeList()
        PokedexRecycler.clearDeletionList()
        Log.d("HSV", "Saiu do multipleSelect")
    }

    companion object {
        const val SECTION_TRACKER_KEY = "selectionTrackerPoke"
        const val FRAGMENT_POKEDEX = "pokedex_id"
    }
}