package com.example.mobile_moviescatalog2023.Domain

import android.icu.text.SimpleDateFormat
import android.util.Patterns
import androidx.compose.runtime.MutableState
import com.example.mobile_moviescatalog2023.Repository.UserProfile.ProfileRequest
import com.example.mobile_moviescatalog2023.Repository.UserProfile.UserProfile
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken
import com.example.mobile_moviescatalog2023.ViewModel.ProfileViewModel
import com.example.mobile_moviescatalog2023.ui.theme.emailValidationError
import java.util.*

class ProfileUseCase {
    var nickName = ""
    var name = ""
    var email = ""
    var profilePicture = ""
    var isMale = true
    var dateOfBirthDisplay = ""
    var birthDate = ""
    var errorMessage = ""

    private var userId = ""
    private val profileRequest = ProfileRequest()

    // Перевод даты в формат для вывода
    private fun reformatDate(apiDate: String): String {
        val apiFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val displayFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        val displayDate: Date = apiFormat.parse(apiDate)

        return displayFormat.format(displayDate)
    }

    // Получение значений из ответа
    private fun getValues(profileResponse: UserProfile) {
        name = profileResponse.name
        email = profileResponse.email
        profilePicture = profileResponse.avatarLink ?: ""
        isMale = profileResponse.gender == 0
        dateOfBirthDisplay = reformatDate(profileResponse.birthDate)
        birthDate = profileResponse.birthDate
        userId = profileResponse.id
        nickName = profileResponse.nickName
    }

    // Получение данных профиля из Api
    suspend fun getProfile(isLoaded: MutableState<Boolean>) {
        val response = profileRequest.getProfile()

        getValues(response)
        isLoaded.value = true
    }

    // Сохранение изменений
    suspend fun saveProfile(vm: ProfileViewModel) {
        val newProfile = UserProfile(
            id = userId,
            nickName = nickName,
            email = vm.email.value,
            avatarLink = vm.profilePicture.value,
            name = vm.name.value,
            birthDate = vm.birthDate.value,
            gender = if (vm.isMale.value) 0 else 1
        )

        profileRequest.putProfile(newProfile)
        getValues(newProfile)
    }

    // Выход из профиля
    suspend fun exit() {
        AuthorizationToken.token = profileRequest.exit().token
    }

    fun validateEmail(email: String): String {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return emailValidationError
        else
            return ""
    }

    // Проверка изменений профиля
    fun checkChanges(vm: ProfileViewModel): Boolean {
        return (isMale != vm.isMale.value ||
                (email != vm.email.value) ||
                (profilePicture != vm.profilePicture.value) ||
                (birthDate != vm.birthDate.value) ||
                (name != vm.name.value))
    }
}