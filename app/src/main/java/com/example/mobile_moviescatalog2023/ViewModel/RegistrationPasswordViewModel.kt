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

    // Регистрация пользователя
    fun onRegistrateButtonClick() {
        RegistrationData.password = password.value

        val api = RetrofitImplementation().registrationApiImplementation()
        var tokenResponse: TokenResponse? = null

        CoroutineScope(Dispatchers.IO).launch {
            val response = api.register(body = RegistrationBody(
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
        }
    }
}