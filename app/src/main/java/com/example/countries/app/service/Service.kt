package com.example.countries.app.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Service {

    private val serviceCalls: MethodService

    companion object {
        const val BASE_URL = "https://api.mercadolibre.com/"
        const val VERSION = "v2/"
        const val FULL_URL = "$BASE_URL$VERSION"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        serviceCalls = retrofit.create(MethodService::class.java)
    }

    suspend fun searchItems(item: String): MELIResponse {
        return serviceCalls.getSearchItems(item)
    }

    suspend fun getItem(itemId: String): Item {
        return serviceCalls.getItem(itemId)
    }
}