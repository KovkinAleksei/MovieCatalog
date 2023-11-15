package com.example.mobile_moviescatalog2023.Domain

import android.util.Patterns
import com.example.mobile_moviescatalog2023.Repository.Registration.RegistrationRequest
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken
import com.example.mobile_moviescatalog2023.ViewModel.RegistrationData
import com.example.mobile_moviescatalog2023.ViewModel.RegistrationViewModel
import com.example.mobile_moviescatalog2023.ui.theme.emailValidationError
import com.example.mobile_moviescatalog2023.ui.theme.loginExistsError
import com.example.mobile_moviescatalog2023.ui.theme.noConnectionError
import com.example.mobile_moviescatalog2023.ui.theme.passwordLengthError
import java.net.UnknownHostException

class RegistrationUseCase {
    var errorMessage = ""
    var isRegistrationAvailable = false

    private val registrationRequest = RegistrationRequest()

    // Проверка корректности указанной электронной почты
    private fun validateEmail(email: String) {
        errorMessage =
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                emailValidationError
            else
                ""
    }

    // Проверка корректности ввода пароля
    private fun validatePassword(password: String) {
        if (password.length < 6) {
            errorMessage = passwordLengthError
            isRegistrationAvailable = false
        } else {
            errorMessage = ""
            isRegistrationAvailable = true
        }
    }

    // Сохранение данных, введённых на первом экране регистрации
    fun saveFirstDataPart(vm: RegistrationViewModel) {
        validateEmail(vm.email.value)

        RegistrationData.birthDate = vm.birthDate.value
        RegistrationData.gender = if (vm.maleSelected.value) 0 else 1
        RegistrationData.userName = vm.userName.value
        RegistrationData.email = vm.email.value
        RegistrationData.name = vm.name.value
    }

    // Регистрация пользователя
    suspend fun register(password: String) {
        validatePassword(password)

        if (!isRegistrationAvailable)
            return

        RegistrationData.password = password

        try {
            val registrationResponse = registrationRequest.register()
            AuthorizationToken.token = registrationResponse.token
        } catch (e: UnknownHostException) {
            errorMessage = noConnectionError
            isRegistrationAvailable = false
        } catch (e: java.lang.Exception) {
            isRegistrationAvailable = false
            errorMessage = loginExistsError
        }
    }
}