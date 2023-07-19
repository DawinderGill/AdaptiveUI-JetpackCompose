package com.dawinder.adaptiveui_jetpackcompose.ui.composables

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dawinder.adaptiveui_jetpackcompose.nav.NavType

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, navigationType: NavType) {
    Row(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavType.HALF_NAVIGATION) {
            LeftNavigationRailDrawerContent(navController)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxSize()) {
                NavigationScreens(navController = navController)
            }
            AnimatedVisibility(visible = navigationType == NavType.BOTTOM_NAVIGATION) {
                BottomNavigationBar(navController)
            }
        }
    }
}
