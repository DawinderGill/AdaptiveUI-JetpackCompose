package com.dawinder.adaptiveui_jetpackcompose.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dawinder.adaptiveui_jetpackcompose.R
import com.dawinder.adaptiveui_jetpackcompose.nav.NavSealedItem
import com.dawinder.adaptiveui_jetpackcompose.nav.NavType
import com.dawinder.adaptiveui_jetpackcompose.ui.theme.md_theme_light_outlineVariant
import com.dawinder.adaptiveui_jetpackcompose.ui.theme.md_theme_light_primary
import com.dawinder.adaptiveui_jetpackcompose.ui.theme.typography
import kotlinx.coroutines.launch

@Composable
fun LeftNavigationDrawer(
    navigationType: NavType,
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    println("Navigation Type : $navigationType")

    if (navigationType == NavType.PERMANENT_NAVIGATION_DRAWER) {
        println("in permanent drawer")
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet {
                    LeftNavigationDrawerContent(navController = navController)
                }
            }
        ) {
            MainScreen(navController = navController, navigationType = navigationType)
        }
    } else {
        println("in drawer else")
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    LeftNavigationDrawerContent(navController = navController)
                }
            },
            drawerState = drawerState
        ) {
            MainScreen(navController = navController, navigationType = navigationType)
        }
    }
}

@Composable
fun LeftNavigationDrawerContent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navSealedItems =
        listOf(NavSealedItem.Home, NavSealedItem.Search, NavSealedItem.List, NavSealedItem.Profile)
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(md_theme_light_outlineVariant)
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = typography.titleMedium,
            color = md_theme_light_primary,
            modifier = modifier.padding(bottom = 16.dp)
        )

        navSealedItems.forEachIndexed { index, item ->
            NavigationDrawerItem(
                selected = selectedItem == index,
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
                onClick = {
                    selectedItem = index
                    navController.navigate(item.path) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun LeftNavigationRailDrawerContent(navController: NavHostController) {
    val navSealedItems =
        listOf(NavSealedItem.Home, NavSealedItem.Search, NavSealedItem.List, NavSealedItem.Profile)
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    NavigationRail {
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