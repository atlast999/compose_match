package org.company.app

import androidx.compose.runtime.Composable
import org.company.app.presentation.ScreenRoot
import org.company.app.theme.AppTheme

@Composable
internal fun App() = AppTheme {
//    LaunchUI(
//        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)
//    )
    ScreenRoot()
}
