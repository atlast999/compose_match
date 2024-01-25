package org.company.app.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.ScaleTransition
import cafe.adriel.voyager.transitions.SlideTransition
import org.company.app.di.appModules
import org.company.app.presentation.authentication.login.LoginScreen
import org.koin.compose.KoinApplication

@Composable
internal fun ScreenRoot() {
    KoinApplication(
        application = {
            modules(appModules())
        }
    ) {
        Navigator(LoginScreen) { navigator ->
            Scaffold(
                topBar = {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = navigator::pop,
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    }
                },
                content = {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(it),
                        contentAlignment = Alignment.Center,
                    ) {
                        FadeTransition(navigator = navigator)
                    }
                },
            )
        }
    }
}