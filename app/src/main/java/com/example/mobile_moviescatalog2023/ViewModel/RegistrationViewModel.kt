package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Domain.RegistrationUseCase

class RegistrationViewModel : ViewModel() {
    val name = mutableStateOf("")
    val maleSelected = mutableStateOf(true)
    var email = mutableStateOf("")
    val isOpenedCalendar = mutableStateOf(false)
    val isClicked = mutableStateOf(false)
    val dateOfBIrthDisplay = mutableStateOf("")
    val birthDate = mutableStateOf("")
    val userName = mutableStateOf("")
    val errorMessage = mutableStateOf("")

    private val registrationUseCase = RegistrationUseCase()

    // Нажатие кнопки продолжить
    fun continueButtonClick() {
        registrationUseCase.saveFirstDataPart(this)

        errorMessage.value = registrationUseCase.errorMessage
    }
}