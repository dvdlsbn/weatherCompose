package net.anobsil.weatherCompose.data.repository

import net.anobsil.weatherCompose.data.WeatherApiService
import net.anobsil.weatherCompose.domain.WeatherData
import net.anobsil.weatherCompose.domain.InterfaceRepository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApiService : WeatherApiService) : WeatherRepository {
    override suspend fun getWeather(city: String): WeatherData = weatherApiService.getWeather(city)
}