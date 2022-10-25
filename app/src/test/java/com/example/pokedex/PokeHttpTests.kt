package com.example.pokedex

import android.util.Log
import com.example.pokedex.repository.http.DetailsHttpUtils
import com.example.pokedex.repository.http.Endpoint
import com.example.pokedex.repository.http.HomeHttpUtils
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.assertj.core.api.Java6Assertions.*
import org.mockito.kotlin.whenever

class PokeHttpTests {

    private lateinit var homeHttp: HomeHttpUtils
    private lateinit var detailsHttp: DetailsHttpUtils
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun before_each_test(){
        mockWebServer = MockWebServer()

        val remote = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Endpoint::class.java)

        homeHttp = HomeHttpUtils(remote)
        detailsHttp = DetailsHttpUtils(remote)
    }

    @After
    fun after_each_test(){
        mockWebServer.shutdown()
    }
}