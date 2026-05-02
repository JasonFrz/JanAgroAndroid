package com.example.janagroandroid.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.janagroandroid.data.local.entity.UserEntity
import com.example.janagroandroid.data.repository.AppRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    app: Application,
    private val repo: AppRepository
) : AndroidViewModel(app) {

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            onResult(repo.login(email, password))
        }
    }

    suspend fun register(
        name: String,
        username: String,
        email: String,
        password: String,
        dob: String,
        gender: String,
        role: String
    ): Boolean {
        return repo.register(
            UserEntity(
                name = name,
                username = username,
                email = email,
                password = password,
                dateOfBirth = dob,
                gender = gender,
                role = role
            )
        )
    }

    fun register(
        name: String, username: String, email: String, password: String,
        dob: String, gender: String, role: String, onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            onResult(register(name, username, email, password, dob, gender, role))
        }
    }
}