package net.anobsil.weatherCompose.domain.InterfaceRepository

import net.anobsil.weatherCompose.domain.WeatherData

fun interface WeatherRepository {
        suspend fun getWeather(city: String): WeatherData
}