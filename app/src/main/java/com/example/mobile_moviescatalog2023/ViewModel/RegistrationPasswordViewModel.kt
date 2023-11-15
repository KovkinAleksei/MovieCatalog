package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Domain.RegistrationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationPasswordViewModel : ViewModel() {
    val isEnabledRegButton = mutableStateOf(false)
    val password = mutableStateOf("")
    val errorMessage = mutableStateOf("")
    val isRegistrationAvailable = mutableStateOf(false)

    private val registrationUseCase = RegistrationUseCase()

    // Регистрация пользователя
    fun onRegistrationButtonClick() {
        CoroutineScope(Dispatchers.Default).launch {
            registrationUseCase.register(password.value)

            isRegistrationAvailable.value = registrationUseCase.isRegistrationAvailable
            errorMessage.value = registrationUseCase.errorMessage
        }
    }
}