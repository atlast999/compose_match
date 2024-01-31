package org.company.app.presentation.chat.room

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.navigator.LocalNavigator
import org.company.app.di.getScreenModel
import org.company.app.domain.models.ChatRoom
import org.company.app.presentation.AppScreen
import org.company.app.presentation.ScreenWrapper

object ChatRoomScreen : AppScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current ?: return
        val screenModel = getScreenModel<ChatRoomScreenModel>()
        val screenState by screenModel.state.collectAsState()
        val openCreateDialog = remember { mutableStateOf(false) }
        ScreenWrapper(
            isLoading = screenState.isLoading,
            title = "Chat rooms",
            navigationAction = {
                navigator.pop()
            },
            actions = {
                IconButton(
                    onClick = {
                        openCreateDialog.value = true
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                }
                IconButton(
                    onClick = screenModel::fetchRooms,
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                    )
                }
            }
        ) {
            if (openCreateDialog.value) {
                NewRoomDialog(
                    onDismissRequest = {
                        openCreateDialog.value = false
                    },
                    onCreateClicked = screenModel::createRoom,
                )
                return@ScreenWrapper
            }
            ChatRoomUI(
                rooms = screenState.rooms,
                onRoomClicked = screenModel::joinChatRoom,
            )
        }
    }

    @Composable
    private fun ChatRoomUI(
        rooms: List<ChatRoom>,
        onRoomClicked: (ChatRoom) -> Unit,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(rooms) { room ->
                ChatRoomItem(
                    modifier = Modifier.fillMaxWidth(),
                    room = room,
                    onRoomClicked = onRoomClicked,
                )

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ChatRoomItem(
        modifier: Modifier,
        room: ChatRoom,
        onRoomClicked: (ChatRoom) -> Unit,
    ) {
        Card(
            onClick = { onRoomClicked.invoke(room) },
            modifier = modifier.padding(10.dp),
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Image(
                    imageVector = Icons.Default.People,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                        .background(color = Color.Transparent, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = room.name
                )
            }
        }
    }

    @Composable
    private fun NewRoomDialog(
        onDismissRequest: () -> Unit,
        onCreateClicked: (String) -> Unit,
    ) {
        var name by remember {
            mutableStateOf("")
        }
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            )
        ) {
            Card(
                modifier = Modifier.height(200.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                ) {
                    Text(
                        text = "Enter room's name:"
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            onDismissRequest.invoke()
                            onCreateClicked.invoke(name)
                        }
                    ) {
                        Text(
                            text = "Create"
                        )
                    }
                }
            }
        }
    }
}

