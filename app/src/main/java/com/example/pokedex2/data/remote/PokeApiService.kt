package com.example.pokedex2.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class PokeApiService() {
    companion object {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json { ignoreUnknownKeys = true }
                )
            }
        }
    }

}