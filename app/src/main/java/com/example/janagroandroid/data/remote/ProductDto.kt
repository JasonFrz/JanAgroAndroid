package com.example.janagroandroid.data.remote.dto

import com.example.janagroandroid.data.local.entity.ProductEntity

data class ProductDto(
    val id: Long? = null,
    val name: String,
    val price: Double,
    val stock: Int,
    val imageUrl: String,
    val description: String,
    val category: String = ""
)

fun ProductDto.toEntity(ownerId: Long = 0): ProductEntity {
    return ProductEntity(
        id = id ?: 0,
        ownerId = ownerId,
        name = name,
        price = price,
        stock = stock,
        imageUrl = imageUrl,
        description = description,
        category = category
    )
}