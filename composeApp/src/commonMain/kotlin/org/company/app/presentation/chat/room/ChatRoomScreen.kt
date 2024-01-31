package org.company.app.presentation.chat.room

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.company.app.presentation.ScreenWrapper

object ChatRoomScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current ?: return
        ScreenWrapper(
            title = "Chat rooms",
            navigationAction = {
                navigator.pop()
            }
        ) {
            ChatRoomUI()
        }
    }

    @Composable
    private fun ChatRoomUI() {

    }
}

