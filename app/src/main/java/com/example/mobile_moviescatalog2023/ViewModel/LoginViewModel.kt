package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Domain.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val isFilledLogin = mutableStateOf(false)
    val isFilledPassword = mutableStateOf(false)
    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val errorMessage = mutableStateOf("")
    val isLoginAvailable = mutableStateOf(true)

    private val loginUseCase = LoginUseCase()

    // Вход в аккаунт
    fun login() {
        CoroutineScope(Dispatchers.Default).launch {
            loginUseCase.login(username.value, password.value)

            errorMessage.value = loginUseCase.errorMessage
            isLoginAvailable.value = loginUseCase.isLoginAvailable
        }
    }
}