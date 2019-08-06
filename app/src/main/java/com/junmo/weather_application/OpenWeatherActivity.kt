package com.junmo.weather_application

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_open_weather.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_weather)

        setting.setOnClickListener {
            startActivity(Intent(this, AccountSettingActivity::class.java))
            requestCurrentWeather()
        }
    }

    private fun requestCurrentWeather() {
        (application as WeatherApplication)
            .requestService()
            ?.getWeatherInfoOfLocation("London", "8f542c094a6cb47cb3b1c75fd007db2a")
            ?.enqueue(object : Callback<TotalWeather> {
                override fun onFailure(call: Call<TotalWeather>, t: Throwable) {
                }

                override fun onResponse(call: Call<TotalWeather>, response: Response<TotalWeather>) {
                    var totalWeather = response.body()
                    Log.d("TAG", totalWeather?.main?.temp_max.toString())
                }
            })

    }
}
