package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Repository.Login.LoginBody
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.Repository.TokenResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    // Данные экрана
    val isFilledLogin = mutableStateOf(false)
    val isFilledPassword = mutableStateOf(false)
    val username = mutableStateOf("")
    val password = mutableStateOf("")

    // Вход в аккаунт
    fun login() {
        val loginRetrofit = RetrofitImplementation()
        val api = loginRetrofit.loginApiImplementation()
        var tokenResponse: TokenResponse? = null

        CoroutineScope(Dispatchers.Default).launch {
            val body = LoginBody(username = username.value, password = password.value)
            val response = api.login(body = body)
            tokenResponse = response

            if (tokenResponse != null)
                AuthorizationToken.token = tokenResponse!!.token
        }
    }
}