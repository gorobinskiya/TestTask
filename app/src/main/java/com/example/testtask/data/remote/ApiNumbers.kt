package com.example.testtask.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiNumbers {
    @GET("{number}?json")
    suspend fun getByNumber(
        @Path("number") string: String,
    ): Response<NumberDto>

    @GET("random/math?json")
    suspend fun getRandom(): Response<NumberDto>

}
