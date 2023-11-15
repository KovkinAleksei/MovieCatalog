package com.example.mobile_moviescatalog2023.Repository.Login

import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.Repository.TokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Запрос для авторизации пользователя
class LoginRequest {
    suspend fun login(username: String, password: String): TokenResponse {
        val loginRetrofit = RetrofitImplementation()
        val api = loginRetrofit.loginApiImplementation()

        val body = LoginBody(username = username, password = password)

        return withContext(Dispatchers.Default) {
            api.login(body = body)
        }
    }
}