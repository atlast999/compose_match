package org.company.app.presentation.authentication.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.company.app.di.getScreenModel
import org.company.app.presentation.home.HomeScreen

object LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current ?: return
        val viewModel = getScreenModel<LoginScreenModel>()
        val screenState by viewModel.state.collectAsState()
        LoginUI(
            username = screenState.username,
            onUsernameChanged = viewModel::onUsernameChanged,
            onLoginClicked = {
                navigator.push(HomeScreen(
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