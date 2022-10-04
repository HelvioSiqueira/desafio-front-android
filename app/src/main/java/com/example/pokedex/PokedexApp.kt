package com.example.pokedex

import android.app.Application
import com.example.pokedex.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokedexApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PokedexApp)
            modules(listOf(androidModule))
        }
    }

    override fun onTerminate() {
        super.onTerminate()

    }
}