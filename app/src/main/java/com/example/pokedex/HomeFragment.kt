package com.example.pokedex

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.pokedex.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private lateinit var bindind: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindind = FragmentHomeBinding.inflate(layoutInflater)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return bindind.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("HSV", "HomeFragment startado")
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

    companion object{
        const val EXTRA_RESULT = "search"
    }

}