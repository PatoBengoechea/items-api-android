package com.example.countries.app.service

import retrofit2.http.GET
import retrofit2.http.Path

interface MethodService {
    @GET("/sites/MLA/search?q=Motorola")
    suspend fun getSearchItems(): MELIResponse

    @GET("/items/{itemId}")
    suspend fun getItem(@Path("itemId")id: String): Item
}