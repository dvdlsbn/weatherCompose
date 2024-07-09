package net.anobsil.weatherCompose.data

import net.anobsil.weatherCompose.domain.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather(@Query("q") city: String): WeatherData
}
