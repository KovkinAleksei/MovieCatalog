package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Repository.Registration.RegistrationBody
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.Repository.TokenResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationPasswordViewModel: ViewModel() {
    val isEnabledRegButton = mutableStateOf(false)
    val password = mutableStateOf("")
    val errorMessage = mutableStateOf("")
    val isRegistrationAvailable = mutableStateOf(false)

    // Проверка корректности ввода пароля
    private fun validatePassword() {
        if (password.value.length < 6) {
            errorMessage.value = "Длина пароля должна составлять не менее 6 символов."
            isRegistrationAvailable.value = false
        }
        else {
            errorMessage.value = ""
            isRegistrationAvailable.value = true
        }
    }

    // Регистрация пользователя
    fun onRegistrateButtonClick() {
        validatePassword()

        if (!isRegistrationAvailable.value)
            return

        RegistrationData.password = password.value

        val api = RetrofitImplementation().registrationApiImplementation()
        var tokenResponse: TokenResponse? = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
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

                tokenResponse = response

                if (tokenResponse != null)
                    AuthorizationToken.token = tokenResponse!!.token
            } catch (e: java.lang.Exception) {
                isRegistrationAvailable.value = false
                errorMessage.value = "Пользователь с таким логином уже существует."
            }
        }
    }
}