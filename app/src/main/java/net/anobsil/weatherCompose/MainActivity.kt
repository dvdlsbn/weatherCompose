package net.anobsil.weatherCompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import net.anobsil.weatherCompose.data.viewmodel.MainViewmodel
import net.anobsil.weatherCompose.ui.LazyColumnRefresh
import net.anobsil.weatherCompose.ui.theme.WeatherComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel: MainViewmodel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition { viewmodel.keepSlpashOnScreen.value }
        setContent {
            WeatherComposeTheme {
                val pullToRefreshState = rememberPullToRefreshState()
                Box(Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
                    var isRefreshing by remember { mutableStateOf(false) }
                    val weatherDataList = viewmodel.weatherData.observeAsState()
                    val onRefreshLambda: () -> Unit = {
                        isRefreshing = true
                        viewmodel.postWeatherList()
                            .invokeOnCompletion {
                                isRefreshing = false
                            }
                    }
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                        ) {
                            Text(text = "Température : élevée")
                            Text(text = "Location : Paris")
                        }
                        LazyColumnRefresh(
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
                            nestedScrollConnection = pullToRefreshState.nestedScrollConnection
                        )
                        if (pullToRefreshState.isRefreshing) {
                            LaunchedEffect(Unit) {
                                onRefreshLambda()
                            }
                        }
                        LaunchedEffect(isRefreshing) {
                            if (isRefreshing) pullToRefreshState.startRefresh() else pullToRefreshState.endRefresh()
                        }
                    }
                    Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = { isRefreshing = true }) {
                        Text(text = "Refresh")
                    }
                    PullToRefreshContainer(
                        state = pullToRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}
