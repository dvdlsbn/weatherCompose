package net.anobsil.weatherCompose.data.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import net.anobsil.weatherCompose.data.viewmodel.interfaceUsecase.GetWeatherUseCase
import net.anobsil.weatherCompose.domain.WeatherData
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {
    private var _weatherData: MutableLiveData<List<WeatherData>> = MutableLiveData()
    val weatherData: LiveData<List<WeatherData>> = _weatherData
    private var _keepOnScreen = mutableStateOf(true)
    val keepOnScreen : State<Boolean> = _keepOnScreen

    init {
        viewModelScope.launch { delay(3_000) }
        _keepOnScreen.value = false
    }

    fun postWeatherList(city: String = "Paris") : Job = viewModelScope.launch {
            val weatherData = emptyList<WeatherData>().toMutableList()
            _weatherData.value?.let { weatherData.addAll(it) }
            weatherData.apply {
                addAll(_weatherData.value ?: emptyList())
                addAll(listOf(getWeatherUseCase(city)))
            }
            _weatherData.value = weatherData
        }
}