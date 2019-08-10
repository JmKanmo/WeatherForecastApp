package com.junmo.weather_application

import android.accounts.AuthenticatorDescription
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TotalWeather(
    var main: Main? = null,
    @SerializedName("weather")
    var weatherList: ArrayList<Weather>? = null
) : Serializable

class Weather(
    var description: String? = null,
    var icon: String? = null,
    var main: String? = null
) : Serializable

class Main(
    var humidity: Int? = null,
    var pressure: Int? = null,
    var temp: Float? = null,
    @SerializedName("temp_max")
    var temp_max: Float? = null,
    @SerializedName("temp_min")
    var temp_min: Float? = null
) : Serializable






