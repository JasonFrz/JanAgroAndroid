package com.example.janagroandroid.data.remote

data class RemoteProductDto(
    val name: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val imageUrl: String,
    val description: String
)

data class RemoteProductResponse(
    val success: Boolean,
    val data: List<RemoteProductDto> = emptyList()
)
