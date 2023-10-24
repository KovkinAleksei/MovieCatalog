package com.example.mobile_moviescatalog2023.Repository

import kotlinx.serialization.Serializable

@Serializable
object RegistrationBody {
    var userName: String = ""
    var name: String = ""
    var password: String = ""
    var email: String = ""
    var birthDate: String = ""
    var gender: Int = 0
}