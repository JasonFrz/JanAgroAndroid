package com.example.janagroandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val username: String, // Field baru
    val email: String,
    val password: String,
    val dateOfBirth: String, // Field baru
    val gender: String, // Field baru
    val role: String,
    val profilePicture: String? = null,
    val isLoggedIn: Boolean = false
)