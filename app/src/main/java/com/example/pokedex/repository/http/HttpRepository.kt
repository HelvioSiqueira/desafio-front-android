package com.example.pokedex.repository.http

//Excluir essa classe quando deixar de ser usada(Ela foi substituida pela DetailsHttpUtils)
class HttpRepository(private val api: Endpoint) {
    
    suspend fun listTypes() = api.getPokeTypes()
    suspend fun pokeListType(url: String) = api.getPokeListTypes(url)
}