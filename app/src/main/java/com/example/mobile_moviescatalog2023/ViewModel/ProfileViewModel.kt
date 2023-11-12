package com.example.mobile_moviescatalog2023.ViewModel

import android.icu.text.SimpleDateFormat
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Repository.Login.LoginBody
import com.example.mobile_moviescatalog2023.Repository.Login.LogoutResponse
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.Repository.TokenResponse
import com.example.mobile_moviescatalog2023.Repository.UserProfile.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ProfileViewModel: ViewModel() {
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

    private var profileResponse: UserProfile? = null

    // Выход из профиля
    fun exit() {
        val retrofit = RetrofitImplementation()
        val api = retrofit.logoutImplementation()

        var logoutResponse: LogoutResponse? = null

        CoroutineScope(Dispatchers.IO).launch {
            val response = api.logout()
            logoutResponse = response

            if (response != null)
                AuthorizationToken.token = logoutResponse!!.token
        }
    }

    // Сохранение изменений
    fun saveButtonClick() {
        val newProfile = UserProfile(
            id = profileResponse!!.id,
            nickName = profileResponse!!.nickName,
            email = email.value,
            avatarLink = profilePicture.value,
            name = name.value,
            birthDate = birthDate.value,
            gender = if (isMale.value) 0 else 1
        )

        val retrofit = RetrofitImplementation()
        val api = retrofit.putProfileImplementation()

        CoroutineScope(Dispatchers.Default).launch {
            val response = api.putProfile(body = newProfile, token = "Bearer ${AuthorizationToken.token}")
        }

        nameDisplay.value = name.value
        isSaveAvailable.value = false

        profileResponse = newProfile
    }

    // Отмена изменений профиля
    fun cancelButtonClick() {
        getValues()
        isSaveAvailable.value = false
    }

    // Проверка изменений профиля
    fun checkChanges() {
        if (profileResponse != null){
            isSaveAvailable.value = ((profileResponse!!.gender == 0) != isMale.value ||
                    (profileResponse!!.email != email.value) ||
                    ((profileResponse?.avatarLink ?: "") != profilePicture.value) ||
                    (profileResponse!!.birthDate != birthDate.value) ||
                    (profileResponse!!.name != name.value))
        }
    }

    // Перевод даты в формат для вывода
    private fun reformatDate(apiDate: String): String {
        val apiFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val displayFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        val displayDate: Date = apiFormat.parse(apiDate)

        return displayFormat.format(displayDate)
    }

    // Получение значений из ответа
    private fun getValues() {
        if (profileResponse == null)
            return

        nameDisplay.value = profileResponse!!.name
        name.value = profileResponse!!.name
        email.value = profileResponse!!.email
        profilePicture.value = profileResponse!!.avatarLink ?: ""
        isMale.value = profileResponse!!.gender == 0
        dateOfBirthDisplay.value = reformatDate(profileResponse!!.birthDate)
        birthDate.value = profileResponse!!.birthDate
    }

    // Получение данных профиля из Api
    fun getProfile(isLoaded: MutableState<Boolean>) {
        isInitialized = true

        val profileRetrofit = RetrofitImplementation()
        val api = profileRetrofit.getProfileImplementation()

        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getProfile(token = "Bearer ${AuthorizationToken.token}")
            profileResponse = response

            getValues()

            isLoaded.value = true
        }
    }
}