package org.company.app.presentation.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.company.app.di.getScreenModel
import org.company.app.entity.RocketLaunch

data class HomeScreen(private val username: String) : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<HomeScreenModel>()
        val screenState by viewModel.state.collectAsState()
        if (screenState.isLoading) {
            CircularProgressIndicator()
            return
        }
        WelcomeUI(
            username = username,
            launches = screenState.launches,
            onLoadMoreClicked = viewModel::loadMore,
        )

    }

    @Composable
    private fun WelcomeUI(
        username: String,
        launches: List<RocketLaunch>,
        onLoadMoreClicked: () -> Unit,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Text(
                    text = "Welcome $username"
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Button(
                    onClick = onLoadMoreClicked,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text("Load more")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            if (launches.isNotEmpty()) {
                items(launches) { launch ->
                    Text(
                        text = buildAnnotatedString {
                            append(launch.missionName)
                            appendLine()
                            append(launch.details)
                            appendLine()
                            append(launch.launchDateUTC)
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}