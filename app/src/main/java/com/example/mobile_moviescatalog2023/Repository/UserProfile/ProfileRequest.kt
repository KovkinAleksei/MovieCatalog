package com.example.mobile_moviescatalog2023.Repository.UserProfile

import com.example.mobile_moviescatalog2023.Repository.Login.LogoutResponse
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRequest {
    // Запрос для получения профиля пользователя
    suspend fun getProfile(): UserProfile {
        val profileRetrofit = RetrofitImplementation()
        val api = profileRetrofit.getProfileImplementation()

        return withContext(Dispatchers.Default) {
            api.getProfile(token = "Bearer ${AuthorizationToken.token}")
        }
    }

    // Запрос для изменения профиля пользователя
    suspend fun putProfile(newProfile: UserProfile) {
        val retrofit = RetrofitImplementation()
        val api = retrofit.putProfileImplementation()

        api.putProfile(body = newProfile, token = "Bearer ${AuthorizationToken.token}")
    }

    // Запрос для выхода из профиля
    suspend fun exit(): LogoutResponse {
        val retrofit = RetrofitImplementation()
        val api = retrofit.logoutImplementation()

        return withContext(Dispatchers.Default) {
            api.logout()
        }
    }
}