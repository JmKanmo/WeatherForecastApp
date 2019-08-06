package com.junmo.weather_application

import android.app.Application
import android.util.Log
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApplication : Application() {

    var service: Service? = null

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        setUpRetrofit()
    }

    private fun setUpRetrofit() {
        val httpClint = OkHttpClient.Builder()
        httpClint.addNetworkInterceptor(StethoInterceptor())
        val client = httpClint.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        service = retrofit.create(Service::class.java)
    }

    fun requestService(): Service? {
        return service
    }
}