package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInstance {
    @GET("/v1/current.json")
    fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): Call<WeatherResponse>
}
