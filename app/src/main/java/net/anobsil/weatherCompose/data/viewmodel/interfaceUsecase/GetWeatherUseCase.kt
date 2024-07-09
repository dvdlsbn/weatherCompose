package net.anobsil.weatherCompose.data.viewmodel.interfaceUsecase

import net.anobsil.weatherCompose.domain.WeatherData

interface GetWeatherUseCase {
    suspend operator fun invoke(city : String) : WeatherData
}