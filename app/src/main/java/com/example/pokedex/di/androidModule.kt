package com.example.pokedex.di

import com.example.pokedex.adapter.util.API
import com.example.pokedex.home.HomeViewModel
import com.example.pokedex.http.Endpoint
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val androidModule = module {
    single { this }

    viewModel{
        HomeViewModel(repository = get())
    }

    single{
        fun getRetrofitInstace(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    single {
        fun provideServiceApi(retrofit: Retrofit): Endpoint{
            return retrofit.create(Endpoint::class.java)
        }
    }
}