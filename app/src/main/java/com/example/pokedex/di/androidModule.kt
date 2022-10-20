package com.example.pokedex.di

import com.example.pokedex.MainActivity
import com.example.pokedex.util.API
import com.example.pokedex.details.DetailsViewModel
import com.example.pokedex.favorites.PokedexViewModel
import com.example.pokedex.home.HomeViewModel
import com.example.pokedex.repository.PokedexRepository
import com.example.pokedex.repository.http.DetailsHttpUtils
import com.example.pokedex.repository.http.Endpoint
import com.example.pokedex.repository.http.HomeHttpUtils
import com.example.pokedex.repository.http.HttpRepository
import com.example.pokedex.repository.http.model.TypesHttpUtils
import com.example.pokedex.repository.room.PokeDatabase
import com.example.pokedex.repository.room.RoomRepository
import com.example.pokedex.tipes.ListPokeViewModel
import com.example.pokedex.tipes.TypesViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val androidModule = module {
    single { this }

    //Será removida quando suas funções passarem todas pro DetailsHttpUtils
    single {
        HttpRepository(api = get()) as HttpRepository
    }

    single {
        DetailsHttpUtils(api = get())
    }

    single {
        HomeHttpUtils(api = get())
    }

    single {
        TypesHttpUtils(api= get())
    }

    single {
        RoomRepository(PokeDatabase.getDatabase(context = get())) as PokedexRepository
    }

    viewModel{
        HomeViewModel(repository = get())
    }

    viewModel {
        DetailsViewModel(repository = get())
    }

    viewModel {
        PokedexViewModel(repository = get())
    }

    viewModel{
        TypesViewModel(repository = get())
    }

    viewModel {
        ListPokeViewModel(repository = get())
    }

    single {

        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()

        retrofit.create(Endpoint::class.java)
    }
}