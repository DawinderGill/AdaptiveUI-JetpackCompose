package com.dawinder.adaptiveui_jetpackcompose.ui.composables

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dawinder.adaptiveui_jetpackcompose.nav.NavSealedItem
import com.dawinder.adaptiveui_jetpackcompose.nav.NavType

/**
 * Composable function that represents the main screen of the application, containing the navigation components based on the provided navigation type.
 *
 * @param navController The navigation controller used for navigating between screens.
 * @param navigationType The type of navigation to be displayed in the main screen.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, navigationType: NavType) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Render the left navigation rail drawer content if the navigation type is HALF_NAVIGATION
        AnimatedVisibility(visible = navigationType == NavType.HALF_NAVIGATION) {
            LeftNavigationRailDrawerContent(navController)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            Box(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxSize()
            ) {
                NavigationScreens(navController = navController)
            }
            // Render the bottom navigation bar if the navigation type is BOTTOM_NAVIGATION
            AnimatedVisibility(visible = navigationType == NavType.BOTTOM_NAVIGATION) {
                BottomNavigationBar(navController)
            }
        }
    }
}

/**
 * Composable function that represents the content of the left navigation rail drawer.
 * Drawer for medium screens
 *
 * @param navController The navigation controller used for navigating between screens.
 */
@Composable
fun LeftNavigationRailDrawerContent(navController: NavHostController) {
    // Define the list of navigation items in the drawer
    val navSealedItems =
        listOf(NavSealedItem.Home, NavSealedItem.Search, NavSealedItem.List, NavSealedItem.Profile)
    // Keep track of the selected item in the navigation rail drawer
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    NavigationRail {
        // Render the navigation items in the rail drawer
        navSealedItems.forEachIndexed { index, item ->
            NavigationRailItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.path) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}