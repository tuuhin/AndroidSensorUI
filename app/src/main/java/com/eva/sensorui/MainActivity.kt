package com.eva.sensorui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eva.sensorui.ui.theme.SensorUITheme
import com.eva.sensorui.utils.AxisInformation
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensorUITheme {
                val viewModel = koinViewModel<MainViewModel>()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        val sensorData by viewModel.sensorData.collectAsStateWithLifecycle()
                        when (sensorData) {
                            AxisInformation.UnknownInformation -> Text(text = "Unknown information")
                            is AxisInformation.XAxisInformation -> Text(text = "${(sensorData as AxisInformation.XAxisInformation).x}")
                            is AxisInformation.XYAxisInformation -> {
                                Text(text = "two way")
                            }

                            is AxisInformation.XYZAxisInformation -> {
                                Column {
                                    Text(text = (sensorData as AxisInformation.XYZAxisInformation).x.toString())
                                    Text(text = (sensorData as AxisInformation.XYZAxisInformation).y.toString())
                                    Text(text = (sensorData as AxisInformation.XYZAxisInformation).z.toString())
                                }
                            }

                            AxisInformation.LoadingInformation -> Text(text = "Loading information")
                        }
                    }
                }
            }
        }
    }
}
