package com.dawinder.adaptiveui_jetpackcompose.ui.composables

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.dawinder.adaptiveui_jetpackcompose.nav.NavType
import com.dawinder.adaptiveui_jetpackcompose.utils.DevicePosture

@Composable
fun AdaptiveUiApp(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture,
    navController: NavHostController
) {
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

    LeftNavigationDrawer(navigationType, navController)
}