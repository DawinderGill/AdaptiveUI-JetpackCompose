package com.dawinder.adaptiveui_jetpackcompose.ui.composables

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import com.dawinder.adaptiveui_jetpackcompose.nav.NavItem
import com.dawinder.adaptiveui_jetpackcompose.ui.theme.md_theme_light_outlineVariant
import com.dawinder.adaptiveui_jetpackcompose.ui.theme.md_theme_light_primary
import com.dawinder.adaptiveui_jetpackcompose.ui.theme.typography
import com.dawinder.adaptiveui_jetpackcompose.utils.DevicePosture
import com.dawinder.adaptiveui_jetpackcompose.utils.NavigationType
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, navigationType: NavigationType) {
    Row(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavigationType.HALF_NAVIGATION) {
            ReplyNavigationRail(navController)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                NavigationScreens(navController = navController)
            }
            AnimatedVisibility(visible = navigationType == NavigationType.BOTTOM_NAVIGATION) {
                BottomNavigationBar(navController)
            }
        }
    }
}

@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture,
    navController: NavHostController
) {
    // You will add navigation info here
    /**
     * This will help us select type of navigation and content type depending on window size and
     * fold state of the device.
     */
    val navigationType: NavigationType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.HALF_NAVIGATION
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                NavigationType.HALF_NAVIGATION
            } else {
                NavigationType.PERMANENT_NAVIGATION_DRAWER
            }
        }

        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
        }
    }

    ReplyNavigationWrapperUI(navigationType, navController)
}

@Composable
private fun ReplyNavigationWrapperUI(
    navigationType: NavigationType,
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet {
                    NavigationDrawerContent(navController)
                }
            }
        ) {
            MainScreen(navController = navController, navigationType = navigationType)
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    NavigationDrawerContent(
                        navController = navController,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                }
            },
            drawerState = drawerState
        ) {
            MainScreen(navController = navController, navigationType = navigationType)
        }
    }
}

@Composable
fun NavigationDrawerContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onDrawerClicked: () -> Unit = {}
) {
    val navItems = listOf(NavItem.Home, NavItem.Search, NavItem.List, NavItem.Profile)
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(md_theme_light_outlineVariant)
            .padding(24.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = typography.titleMedium,
                color = md_theme_light_primary
            )
            IconButton(onClick = onDrawerClicked) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.navigation_drawer)
                )
            }
        }

        navItems.forEachIndexed { index, item ->
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
fun ReplyNavigationRail(navController: NavHostController) {
    val navItems = listOf(NavItem.Home, NavItem.Search, NavItem.List, NavItem.Profile)
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    NavigationRail {
        navItems.forEachIndexed { index, item ->
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
