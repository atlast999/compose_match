package org.company.app.presentation.authentication.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

object LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current ?: return
        val viewModel = rememberScreenModel { LoginScreenModel() }
        val screenState by viewModel.state.collectAsState()
        LoginUI(
            username = screenState.username,
            onUsernameChanged = viewModel::onUsernameChanged,
            onLoginClicked = {
                navigator.push(WelcomeScreen(
                    username = screenState.username
                ))
            }
        )
    }

    @Composable
    private fun LoginUI(
        username: String,
        onUsernameChanged: (String) -> Unit,
        onLoginClicked: () -> Unit,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = onUsernameChanged,
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
            )
            OutlinedButton(
                onClick = onLoginClicked,
            ) {
                Text(
                    text = "Navigate"
                )
            }
        }
    }
}

data class WelcomeScreen(private val username: String) : Screen {

    @Composable
    override fun Content() {
        WelcomeUI(
            username = username,
        )
    }

    @Composable
    private fun WelcomeUI(
        username: String,
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(color = Color.Red),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Welcome $username"
            )
        }
    }
}