package com.example.weatherapp

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {



    private val apiKey = "500c361e84ac4f2faf355609241501"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherApiService = RetrofitClient.create()
        val location = "Tbilisi"

        val call = weatherApiService.getCurrentWeather(apiKey, location)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    // Handle the weather data here
                    updateUi(weatherResponse)
                } else {
                    // Handle error
                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@MainActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUi(weatherResponse: WeatherResponse?) {
        // Update UI with the weather data
        if (weatherResponse != null) {
            val locationTextView: TextView = findViewById(R.id.textViewLocation)
            val temperatureTextView: TextView = findViewById(R.id.textViewTemperature)
            val humidityTextView: TextView = findViewById(R.id.textviewHumidity)
            val sunriseTextView: TextView = findViewById(R.id.textviewSunrise)
            val sunsetTextView: TextView = findViewById(R.id.textviewSunset)
            val windTextView: TextView = findViewById(R.id.textviewWind)

            val pressureTextView: TextView = findViewById(R.id.textviewPressure)
            val statusTextView: TextView = findViewById(R.id.textviewStatus)


            locationTextView.text = "${weatherResponse.location.name}"

            sunriseTextView.text = weatherResponse.current.sunrise
            sunsetTextView.text = weatherResponse.current.sunset
            windTextView.text = "${weatherResponse.current.wind_kph} kph ${weatherResponse.current.wind_dir}"
            pressureTextView.text = "${weatherResponse.current.pressure_mb} mb"
            statusTextView.text = "${weatherResponse.current.condition.text}"



            temperatureTextView.text = "${weatherResponse.current.temp_c} °C"
            humidityTextView.text = "Humidity: ${weatherResponse.current.humidity}%"
        }
    }

}