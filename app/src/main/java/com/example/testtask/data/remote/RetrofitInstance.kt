package com.example.testtask.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
    }
    val api: ApiNumbers by lazy {
        retrofit.create(ApiNumbers::class.java)
    }
}

const val BASEURL = "http://numbersapi.com/"
