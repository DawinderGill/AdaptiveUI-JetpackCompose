package com.dawinder.adaptiveui_jetpackcompose.ui.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dawinder.adaptiveui_jetpackcompose.nav.NavSealedItem
import com.dawinder.adaptiveui_jetpackcompose.ui.composables.tabs.HomeScreen
import com.dawinder.adaptiveui_jetpackcompose.ui.composables.tabs.ListScreen
import com.dawinder.adaptiveui_jetpackcompose.ui.composables.tabs.ProfileScreen
import com.dawinder.adaptiveui_jetpackcompose.ui.composables.tabs.SearchScreen

/**
 * Composable function that defines the navigation screens and their corresponding destinations.
 *
 * @param navController The navigation controller used for handling navigation between screens.
 */
@Composable
fun NavigationScreens(navController: NavHostController) {
    NavHost(navController, startDestination = NavSealedItem.Home.path) {
        composable(NavSealedItem.Home.path) { HomeScreen() }
        composable(NavSealedItem.Search.path) { SearchScreen() }
        composable(NavSealedItem.List.path) { ListScreen() }
        composable(NavSealedItem.Profile.path) { ProfileScreen() }
    }
}