package com.dawinder.adaptiveui_jetpackcompose.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.WindowInfoTracker
import com.dawinder.adaptiveui_jetpackcompose.ui.composables.AdaptiveUiApp
import com.dawinder.adaptiveui_jetpackcompose.ui.theme.AdaptiveUIJetpackComposeTheme
import com.dawinder.adaptiveui_jetpackcompose.utils.DevicePosture
import com.dawinder.adaptiveui_jetpackcompose.utils.getDevicePostureFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@ExperimentalMaterial3WindowSizeClassApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AdaptiveUIJetpackComposeTheme {
                val windowSize = calculateWindowSizeClass(this)
                val devicePosture = getDevicePosture().collectAsState().value
                val navController = rememberNavController()
                AdaptiveUiApp(windowSize.widthSizeClass, devicePosture, navController)
            }
        }
    }

    private fun getDevicePosture(): StateFlow<DevicePosture> {
        return WindowInfoTracker.getOrCreate(this)
            .getDevicePostureFlow(this, lifecycle)
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )
    }
}

@Preview(showBackground = true)
@Composable
fun CompactUiPreview() {
    AdaptiveUIJetpackComposeTheme {
        val navController = rememberNavController()
        AdaptiveUiApp(
            windowSize = WindowWidthSizeClass.Compact,
            foldingDevicePosture = DevicePosture.NormalPosture, navController
        )
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun MediumUiPreview() {
    AdaptiveUIJetpackComposeTheme {
        val navController = rememberNavController()
        AdaptiveUiApp(
            windowSize = WindowWidthSizeClass.Medium,
            foldingDevicePosture = DevicePosture.NormalPosture, navController
        )
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun ExpandedUiPreview() {
    AdaptiveUIJetpackComposeTheme {
        val navController = rememberNavController()
        AdaptiveUiApp(
            windowSize = WindowWidthSizeClass.Expanded,
            foldingDevicePosture = DevicePosture.NormalPosture, navController
        )
    }
}