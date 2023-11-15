package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Domain.ProfileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class ProfileViewModel : ViewModel() {
    val nameDisplay = mutableStateOf("")
    val name = mutableStateOf("")
    val email = mutableStateOf("")
    val profilePicture = mutableStateOf("")
    val isMale = mutableStateOf(true)
    val dateOfBirthDisplay = mutableStateOf("")
    val birthDate = mutableStateOf("")
    val isClicked = mutableStateOf(false)
    val isSaveAvailable = mutableStateOf(false)
    var isInitialized = false
    var connectionFailed = false
    var errorMessage = mutableStateOf("")

    private val profileUseCase = ProfileUseCase()

    // Получение профиля из Api
    fun getProfile(isLoaded: MutableState<Boolean>) {
        isInitialized = true

        CoroutineScope(Dispatchers.Default).launch {
            try {
                profileUseCase.getProfile(isLoaded)
            } catch (e: UnknownHostException) {
                connectionFailed = true

                return@launch
            }

            copyValues()
        }
    }

    // Сохранение изменений
    fun saveButtonClick() {
        val currentVm = this
        errorMessage.value = profileUseCase.validateEmail(email.value)

        if (!errorMessage.value.isEmpty())
            return

        CoroutineScope(Dispatchers.Default).launch {
            try {
                profileUseCase.saveProfile(currentVm)
            } catch (e: Exception) {
                connectionFailed = true

                return@launch
            }

            isSaveAvailable.value = profileUseCase.checkChanges(currentVm)
        }
    }

    // Выход из профиля
    fun exit(onExit: () -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                profileUseCase.exit()
            } catch (e: UnknownHostException) {
                connectionFailed = true

                return@launch
            }

            withContext(Dispatchers.Main) {
                onExit()
            }
        }
    }

    // Отмена изменений профиля
    fun cancelButtonClick() {
        copyValues()
        isSaveAvailable.value = profileUseCase.checkChanges(this)
    }

    // Проверка изменений профиля
    fun checkChanges() {
        errorMessage.value = ""
        isSaveAvailable.value = profileUseCase.checkChanges(this) &&
                !email.value.isEmpty() && !profilePicture.value.isEmpty() && !name.value.isEmpty() && !birthDate.value.isEmpty()
    }

    // Заполнение данных профиля
    private fun copyValues() {
        nameDisplay.value = profileUseCase.nickName
        name.value = profileUseCase.name
        email.value = profileUseCase.email
        profilePicture.value = profileUseCase.profilePicture
        isMale.value = profileUseCase.isMale
        dateOfBirthDisplay.value = profileUseCase.dateOfBirthDisplay
        birthDate.value = profileUseCase.birthDate
    }
}