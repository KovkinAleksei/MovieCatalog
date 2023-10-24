@file:OptIn(ExperimentalSerializationApi::class)

package com.example.mobile_moviescatalog2023.Repository

import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.create
import okhttp3.logging.HttpLoggingInterceptor

class RetrofitImplementation {
    private val retrofit = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply{
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
        )
        .baseUrl("https://react-midterm.kreosoft.space/api/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    fun loginApiImplementation(): LoginApi {
        return retrofit.create()
    }

    fun registrationApiImplementation(): RegistrationApi {
        return retrofit.create()
    }
}