package org.company.app.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import io.github.aakira.napier.Napier
import org.company.app.di.appModules
import org.company.app.presentation.authentication.login.LoginScreen
import org.koin.compose.KoinApplication

abstract class AppScreen : Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScreenWrapper(
    isLoading: Boolean = false,
    title: String = "",
    navigationAction: () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
        )
    },
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    LaunchedEffect(key1 = true) {
        Napier.i("Start screen: $title")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier,
                        onClick = navigationAction,
                    ) {
                        navigationIcon.invoke()
                    }
                },
                actions = actions,
            )
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize().padding(it).padding(10.dp),
                contentAlignment = Alignment.Center,
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    content.invoke(this)
                }
            }
        },
    )
}

@Composable
internal fun ScreenRoot() {
    KoinApplication(
        application = {
            modules(appModules())
        }
    ) {
        Navigator(LoginScreen) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}