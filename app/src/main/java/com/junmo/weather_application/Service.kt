package com.junmo.weather_application

import com.google.gson.JsonObject
import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("data/2.5/weather/")

    fun getWeatherInfoOfLocation(
        @Query("q") location: String,
        @Query("APPID") appID: String
    ): Call<TotalWeather>

    @GET("data/2.5/weather/")

    fun getWeatherInfoOfCoodinate(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("APPID") appID: String,
        @Query("units") units: String,
        @Query("lang") language: String
    ): Call<TotalWeather>
}