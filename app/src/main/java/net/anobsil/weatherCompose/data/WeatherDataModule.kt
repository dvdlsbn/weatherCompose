package net.anobsil.weatherCompose.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.anobsil.weatherCompose.data.repository.WeatherRepositoryImpl
import net.anobsil.weatherCompose.domain.InterfaceRepository.WeatherRepository
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/"
const val API_KEY_PARAM = "appid"
const val API_KEY_PARAM_TO_MOVE_TO_CONF = "bd5e378503939ddaee76f12ad7a97608"

@Module
@InstallIn(SingletonComponent::class)
object WeatherDataModule {

    @Provides
    @Singleton
    fun provideWeatherRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_WEATHER)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService = retrofit.create(WeatherApiService::class.java)

    @Provides
    fun provideWeatherRepository(weatherApiService: WeatherApiService): WeatherRepository = WeatherRepositoryImpl(weatherApiService)

    private val clientInterceptor = Interceptor { chain: Chain ->
        var request: Request = chain.request()
        val url: HttpUrl = request.url.newBuilder().addQueryParameter(API_KEY_PARAM, API_KEY_PARAM_TO_MOVE_TO_CONF).build()
        request = request.newBuilder().url(url).build()
        chain.proceed(request)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(clientInterceptor)
        .build()
}