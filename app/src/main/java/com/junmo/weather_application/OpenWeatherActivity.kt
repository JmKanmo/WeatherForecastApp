package com.junmo.weather_application

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_open_weather.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.jar.Manifest

class OpenWeatherActivity : AppCompatActivity(), LocationListener {

    private val PERMISSION_REQUEST_CODE = 2000
    private val APP_ID = "8f542c094a6cb47cb3b1c75fd007db2a"
    private val units = "metric"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_weather)

        getLocationInfo()

        setting.setOnClickListener {
            startActivity(Intent(this, AccountSettingActivity::class.java))
        }
    }

    private fun drawCurrentWeather(currentWeather: TotalWeather) {
        with(currentWeather) {
            this.main?.temp?.let { current_now.text = it.toString() }
            this.main?.temp_max?.let { current_max.text = it.toString() }
            this.main?.temp_min?.let { current_min.text = it.toString() }
        }
    }

    private fun getLocationInfo() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        } else {
            val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
//                Log.d("TAG", "1_lattitude:" + latitude.toString())
//                Log.d("TAG", "1_longitude:" + longitude.toString())
                requestWeatherInfoOfLocation(latitude = latitude, longiude = longitude)
            } else {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    3000L,
                    0f,
                    this
                )
                locationManager.removeUpdates(this)
            }
        }
    }

    private fun requestWeatherInfoOfLocation(latitude: Double, longiude: Double) {
        (application as WeatherApplication)
            .requestService()
            ?.getWeatherInfoOfCoodinate(
                latitude = latitude,
                longitude = longiude,
                appID = APP_ID,
                units = units
            )?.enqueue(object : Callback<TotalWeather> {
                override fun onFailure(call: Call<TotalWeather>, t: Throwable) {

                }

                override fun onResponse(call: Call<TotalWeather>, response: Response<TotalWeather>) {
                    if (response.isSuccessful) {
                        val totalWeather = response.body()
                        totalWeather?.let {
                            drawCurrentWeather(it)
                        }
                    }
                }
            })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                getLocationInfo()
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        val latitude = location?.latitude
        val longitude = location?.longitude
//        Log.d("TAG", "2_latitude:" + latitude.toString())
//        Log.d("TAG", "2_longitude:" + longitude.toString())
        if (latitude != null && longitude != null) requestWeatherInfoOfLocation(
            latitude = latitude,
            longiude = longitude
        )
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }
}
