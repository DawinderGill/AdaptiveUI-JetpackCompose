package com.dawinder.adaptiveui_jetpackcompose.ui.composables

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.dawinder.adaptiveui_jetpackcompose.nav.NavType
import com.dawinder.adaptiveui_jetpackcompose.utils.DevicePosture

/**
 * Composable function that represents the adaptive UI of the application based on the device's window size and folding device posture.
 *
 * @param windowSize The window size classification of the device.
 * @param foldingDevicePosture The folding device posture obtained from the device.
 * @param navController The navigation controller used for navigating between screens.
 */
@Composable
fun AdaptiveUiApp(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture,
    navController: NavHostController
) {
    // Determine the navigation type based on the window size and folding device posture
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

    // Render the left navigation drawer based on the determined navigation type
    LeftNavigationDrawer(navigationType, navController)
}