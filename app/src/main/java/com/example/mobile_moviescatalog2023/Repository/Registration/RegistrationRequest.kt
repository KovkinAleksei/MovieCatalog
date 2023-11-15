package com.example.mobile_moviescatalog2023.Repository.Registration

import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.Repository.TokenResponse
import com.example.mobile_moviescatalog2023.ViewModel.RegistrationData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RegistrationRequest {
    // Запрос для регистрации пользователя
    suspend fun register(): TokenResponse {
        val api = RetrofitImplementation().registrationApiImplementation()

        val response = api.register(
            body = RegistrationBody(
                userName = RegistrationData.userName,
                name = RegistrationData.name,
                password = RegistrationData.password,
                email = RegistrationData.email,
                birthDate = RegistrationData.birthDate,
                gender = RegistrationData.gender
            )
        )

        return withContext(Dispatchers.Default) {
            response
        }
    }
}