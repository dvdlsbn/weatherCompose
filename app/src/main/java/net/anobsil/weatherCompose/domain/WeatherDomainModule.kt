package net.anobsil.weatherCompose.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.anobsil.weatherCompose.data.viewmodel.interfaceUsecase.GetWeatherUseCase
import net.anobsil.weatherCompose.domain.InterfaceRepository.WeatherRepository
import net.anobsil.weatherCompose.domain.usecase.GetWeatherUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
object WeatherDomainModule {

    @Provides
    fun provideGetWeatherUseCase(weatherRepository: WeatherRepository): GetWeatherUseCase = GetWeatherUseCaseImpl(weatherRepository)

}