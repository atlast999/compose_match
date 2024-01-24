package org.company.app.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.company.app.cache.Database
import org.company.app.entity.RocketLaunch
import org.company.app.network.SpaceXApi
import org.company.app.provideDatabaseDriverFactory

internal val database = Database(
    databaseDriverFactory = provideDatabaseDriverFactory()
)
internal val spaceXApi = SpaceXApi()

@Composable
fun LaunchUI(
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val launches = remember {
        mutableStateOf(emptyList<RocketLaunch>())
    }
    LaunchedEffect(true) {
        database.clearDatabase()
        launches.value = fetchData()
    }
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        launches.value = fetchData(forceReload = true)
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Reload")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (launches.value.isNotEmpty()) {
            items(launches.value) {launch ->
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

private suspend fun fetchData(forceReload: Boolean = false): List<RocketLaunch> {
    val localLaunches = database.getAllLaunches()
    return if (localLaunches.isEmpty() || forceReload) {
        val launches = spaceXApi.getAllLaunches()
        database.createLaunches(launches = listOf(launches[localLaunches.size]))
        database.getAllLaunches()
    } else {
        localLaunches
    }
}