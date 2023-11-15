package com.example.mobile_moviescatalog2023.Domain

import com.example.mobile_moviescatalog2023.Repository.Login.LoginRequest
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken
import com.example.mobile_moviescatalog2023.ui.theme.noConnectionError
import com.example.mobile_moviescatalog2023.ui.theme.wrongLoginError
import java.net.UnknownHostException

// Авторизация пользователя
class LoginUseCase {
    var errorMessage = ""
    var isLoginAvailable = false

    private val loginRequest = LoginRequest()

    // Авторизация пользователя
    suspend fun login(username: String, password: String) {
        try {
            val loginResponse = loginRequest.login(username, password)
            AuthorizationToken.token = loginResponse.token
            errorMessage = ""
            isLoginAvailable = false
        } catch (e: UnknownHostException) {
            errorMessage = noConnectionError
            isLoginAvailable = true
        } catch (e: Exception) {
            errorMessage = wrongLoginError
            isLoginAvailable = true
        }
    }
}