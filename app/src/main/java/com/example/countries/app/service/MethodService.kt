package com.example.countries.app.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MethodService {
    @GET("/sites/MLA/search")
    suspend fun getSearchItems(@Query("q")item: String): MELIResponse

    @GET("/items/{itemId}")
    suspend fun getItem(@Path("itemId")id: String): Item
}