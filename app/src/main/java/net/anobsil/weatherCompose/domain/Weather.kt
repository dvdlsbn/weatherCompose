package net.anobsil.weatherCompose.domain

import com.google.gson.annotations.SerializedName

data class WeatherData(
    val name: String,
    val main: Main,
    @SerializedName("weather")
    val weatherList: List<Weather>
)

data class Main(
    val temp: Double
)

data class Weather(
    val icon: String
)