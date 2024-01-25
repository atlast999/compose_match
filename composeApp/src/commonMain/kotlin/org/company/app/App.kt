package org.company.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.company.app.cache.DatabaseDriverFactory
import org.company.app.presentation.LaunchUI
import org.company.app.presentation.ScreenRoot
import org.company.app.theme.AppTheme

@Composable
internal fun App() = AppTheme {
//    LaunchUI(
//        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)
//    )
    ScreenRoot()
}

internal expect fun provideDatabaseDriverFactory(): DatabaseDriverFactory