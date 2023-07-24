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
import com.dawinder.adaptiveui_jetpackcompose.nav.NavType
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

        /**
         * Sets the content of the application using Jetpack Compose and adaptive UI features.
         * The content consists of an AdaptiveUIJetpackComposeTheme wrapping an AdaptiveUiApp composable.
         *
         * windowSize: Calculate window size classification based on screen size
         * devicePosture: Collect device posture as a state value
         * navigationType: Determine navigation type based on window size and device posture
         * navController: Create a NavHostController for app navigation
         */
        setContent {
            AdaptiveUIJetpackComposeTheme {
                val windowSize = calculateWindowSizeClass(this).widthSizeClass
                val devicePosture = getDevicePosture().collectAsState().value
                val navigationType = getNavigationType(windowSize, devicePosture)
                val navController = rememberNavController()
                AdaptiveUiApp(navigationType, navController)
            }
        }
    }

    /**
     * Retrieves the device's posture as a StateFlow of [DevicePosture] objects.
     *
     * @return A StateFlow emitting the device's posture.
     */
    private fun getDevicePosture(): StateFlow<DevicePosture> {
        return WindowInfoTracker.getOrCreate(this)
            .getDevicePostureFlow(this, lifecycle)
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )
    }

    /**
     * Determines the navigation type based on the window size and folding device posture.
     *
     * @param windowSize The classification of the window size.
     * @param foldingDevicePosture The posture of the folding device.
     * @return The navigation type to be used in the app.
     */
    private fun getNavigationType(
        windowSize: WindowWidthSizeClass,
        foldingDevicePosture: DevicePosture
    ): NavType {
        val navigationType: NavType

        when (windowSize) {
            WindowWidthSizeClass.Medium -> {
                navigationType = NavType.HALF_NAVIGATION
            }

            WindowWidthSizeClass.Expanded -> {
                navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                    NavType.HALF_NAVIGATION
                } else {
                    NavType.PERMANENT_NAVIGATION_DRAWER
                }
            }

            else -> {
                navigationType = NavType.BOTTOM_NAVIGATION
            }
        }
        return navigationType
    }
}

// Preview of compact screen
@Preview(showBackground = true)
@Composable
fun CompactUiPreview() {
    AdaptiveUIJetpackComposeTheme {
        val navController = rememberNavController()
        AdaptiveUiApp(NavType.BOTTOM_NAVIGATION, navController)
    }
}

// Preview of medium screen
@Preview(showBackground = true, widthDp = 700)
@Composable
fun MediumUiPreview() {
    AdaptiveUIJetpackComposeTheme {
        val navController = rememberNavController()
        AdaptiveUiApp(NavType.HALF_NAVIGATION, navController)
    }
}

// Preview of expanded screen
@Preview(showBackground = true, widthDp = 1000)
@Composable
fun ExpandedUiPreview() {
    AdaptiveUIJetpackComposeTheme {
        val navController = rememberNavController()
        AdaptiveUiApp(NavType.PERMANENT_NAVIGATION_DRAWER, navController)
    }
}