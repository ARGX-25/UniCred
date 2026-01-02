package com.example.unicred.models.network.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:8000/"
    // ↑ Android emulator talking to localhost FastAPI

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val studentApi: StudentApi by lazy {
        retrofit.create(StudentApi::class.java)
    }
}
