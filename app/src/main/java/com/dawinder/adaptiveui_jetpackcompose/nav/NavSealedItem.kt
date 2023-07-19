package com.dawinder.adaptiveui_jetpackcompose.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search

sealed class NavSealedItem {
    object Home :
        NavItem(path = NavPath.HOME.toString(), title = NavTitle.HOME, icon = Icons.Default.Home)

    object Search :
        NavItem(path = NavPath.SEARCH.toString(), title = NavTitle.SEARCH, icon = Icons.Default.Search)

    object List :
        NavItem(path = NavPath.LIST.toString(), title = NavTitle.LIST, icon = Icons.Default.List)

    object Profile :
        NavItem(
            path = NavPath.PROFILE.toString(), title = NavTitle.PROFILE, icon = Icons.Default.Person)
}