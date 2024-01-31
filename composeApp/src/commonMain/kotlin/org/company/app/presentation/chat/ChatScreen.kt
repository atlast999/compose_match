package org.company.app.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.company.app.di.getScreenModel

object ChatScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ChatScreenModel>()
        val screenState by viewModel.state.collectAsState()
        if (screenState.isLoading) {
            CircularProgressIndicator()
        } else {
            ChatUI(
                contents1 = screenState.content1,
                onSend1Clicked = viewModel::send1,
                contents2 = screenState.content2,
                onSEnd2Clicked = viewModel::send2,
            )
        }
    }

    @Composable
    fun ChatUI(
        contents1: String,
        onSend1Clicked: (String) -> Unit,
        contents2: String,
        onSEnd2Clicked: (String) -> Unit,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight()
            ) {
                Text(
                    text = contents1,
                    modifier = Modifier.weight(1f),
                )
                var message by remember {
                    mutableStateOf("")
                }
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it }
                )
                OutlinedButton(
                    onClick = {
                        onSend1Clicked.invoke(message)
                        message = ""
                    },
                ) {
                    Text(text = "Send")
                }
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = contents2,
                    modifier = Modifier.weight(1f),
                )
                var message by remember {
                    mutableStateOf("")
                }
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it }
                )
                OutlinedButton(
                    onClick = {
                        onSEnd2Clicked.invoke(message)
                        message = ""
                    },
                ) {
                    Text(text = "Send")
                }
            }
        }
    }
}