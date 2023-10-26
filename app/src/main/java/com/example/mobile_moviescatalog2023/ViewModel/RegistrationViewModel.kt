package com.example.mobile_moviescatalog2023.ViewModel

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegistrationViewModel: ViewModel() {
    val isFilledLogin = mutableStateOf(false)
    val isFilledName = mutableStateOf(false)
    val isFilledEmail = mutableStateOf(false)
    val name = mutableStateOf("")
    val maleSelected = mutableStateOf(true)
    var email = mutableStateOf("")
    val isOpenedCalendar = mutableStateOf(false)
    val isClicked = mutableStateOf(false)
    val dateOfBIrthDisplay = mutableStateOf("")
    val birthDate = mutableStateOf("")
    val userName = mutableStateOf("")
    val errorMessage = mutableStateOf("")

    private fun validateEmail() {
        errorMessage.value =
            if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches())
                "Email введён некорректно"
            else
                ""
    }

    fun continueButtonClick(){
        validateEmail()

        RegistrationData.birthDate=birthDate.value
        RegistrationData.gender= if (maleSelected.value) 0 else 1
        RegistrationData.userName=userName.value
        RegistrationData.email=email.value
        RegistrationData.name=name.value
    }
}