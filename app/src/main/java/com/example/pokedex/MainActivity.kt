package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokedex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bindind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        initViews(bindind)
    }

    private fun initViews(binding: ActivityMainBinding) {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        val navControler = navHostFragment.navController

        binding.bottonNavigation.apply {
            setupWithNavController(navControler)
        }
    }
}