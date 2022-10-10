package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.home.HomeFragment

class MainActivity : AppCompatActivity(), HomeFragment.OnScroll {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bindind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindind = ActivityMainBinding.inflate(layoutInflater)

        bindind.bottonNavigation.labelVisibilityMode

        setContentView(bindind.root)

        initViews(bindind)
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

    }


}