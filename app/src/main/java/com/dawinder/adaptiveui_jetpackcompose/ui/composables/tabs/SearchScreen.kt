package com.dawinder.adaptiveui_jetpackcompose.ui.composables.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dawinder.adaptiveui_jetpackcompose.R
import com.dawinder.adaptiveui_jetpackcompose.ui.theme.md_theme_dark_secondaryContainer
import com.dawinder.adaptiveui_jetpackcompose.ui.theme.typography

/**
 * Composable function that represents the search screen of the application.
 */
@Composable
fun SearchScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(id = R.string.search),
            style = typography.titleLarge,
            color = md_theme_dark_secondaryContainer
        )
    }
}