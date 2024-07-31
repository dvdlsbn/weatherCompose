package net.anobsil.weatherCompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.anobsil.weatherCompose.data.viewmodel.MainViewmodel
import net.anobsil.weatherCompose.ui.PullToRefreshLazyColumn
import net.anobsil.weatherCompose.ui.theme.WeatherComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewmodel: MainViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition { viewmodel.keepOnScreen.value}
        setContent {
            WeatherComposeTheme {
                var isRefreshing by remember { mutableStateOf(false) }
                val weatherDataList = viewmodel.weatherData.observeAsState()
                Box(modifier = Modifier.fillMaxSize()) {

                    PullToRefreshLazyColumn(
                        items = weatherDataList.value ?: emptyList(),
                        content = { weatherData ->
                            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = weatherData.name, modifier = Modifier
                                        .padding(16.dp)
                                )
                                Text(
                                    text = weatherData.main.temp.toString(), modifier = Modifier
                                        .padding(16.dp)
                                )
                                Text(
                                    text = weatherData.weatherList.firstOrNull()?.icon ?: "unknown", modifier = Modifier
                                        .padding(16.dp)
                                )
                            }
                        },
                        isRefreshing = isRefreshing,
                        onRefresh = {
                            isRefreshing = true
                            viewmodel.postWeatherList().invokeOnCompletion {
                                isRefreshing = false
                            }
                        }
                    )
                    Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = { isRefreshing = true }) {
                        Text(text = "Refresh")
                    }
                }
            }
        }
    }
}
