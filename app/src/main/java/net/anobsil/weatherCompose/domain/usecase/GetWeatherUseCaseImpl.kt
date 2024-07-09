package net.anobsil.weatherCompose.domain.usecase

import androidx.compose.runtime.traceEventStart
import net.anobsil.weatherCompose.data.viewmodel.interfaceUsecase.GetWeatherUseCase
import net.anobsil.weatherCompose.domain.InterfaceRepository.WeatherRepository
import net.anobsil.weatherCompose.domain.WeatherData
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(private val weatherRepository : WeatherRepository) : GetWeatherUseCase {
    override suspend fun invoke(city : String) : WeatherData = weatherRepository.getWeather(city)
}