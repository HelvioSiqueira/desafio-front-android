package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.home.HomeFragment

class MainActivity : AppCompatActivity(), HomeFragment.OnScroll {

    private lateinit var navHostFragment: NavHostFragment

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //bindind.bottonNavigation.labelVisibilityMode

        setContentView(binding.root)

        initViews(binding)
    }

    private fun initViews(binding: ActivityMainBinding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        val navControler = navHostFragment.navController

        binding.bottonNavigation.apply {
            setupWithNavController(navControler)
        }
    }

    override fun onListScrolled(status: Boolean) {

        binding.bottonNavigation.visibility =
            if (status) View.VISIBLE
            else View.GONE
    }

}