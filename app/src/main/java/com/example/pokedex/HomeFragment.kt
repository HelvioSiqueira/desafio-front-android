package com.example.pokedex

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.navigation.Navigation
import com.example.pokedex.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private lateinit var bindind: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindind = FragmentHomeBinding.inflate(layoutInflater)
        return bindind.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("HSV", "HomeFragment startado")
    }

    companion object{
        const val TAG_HOME = "tagHome"

        fun newIntance() = HomeFragment()
    }
}