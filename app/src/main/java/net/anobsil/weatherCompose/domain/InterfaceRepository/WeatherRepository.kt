package net.anobsil.weatherCompose.domain.InterfaceRepository

import net.anobsil.weatherCompose.domain.WeatherData

interface WeatherRepository {
        suspend fun getWeather(city: String): WeatherData
}