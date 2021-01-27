package com.example.countries.app.service

import kotlinx.serialization.Serializable

data class MELIResponse(
    val results: List<Item>
)


data class Item(
    val id: String,
    val title: String,
    val secure_thumbnail: String?,
    val price: Float?,
    val tags: List<String>?,
    val accepts_mercadopago: Boolean?,
    val images: List<Image>
)

data class Image(
    val secure_url: String
)