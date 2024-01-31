package org.company.app.presentation.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.company.app.cache.Database
import org.company.app.data.entity.RocketLaunch
import org.company.app.network.SpaceXApi

class HomeScreenModel(
    private val spaceXApi: SpaceXApi,
    private val database: Database,
) : StateScreenModel<HomeScreenModel.State>(State()){

    init {
        screenModelScope.launch {
            database.clearDatabase()
            loadMore()
        }
    }

    fun loadMore() = screenModelScope.launch {
        val count = state.value.launches.size
        mutableState.update {
            it.copy(isLoading = true)
        }
        val fetchedLaunches = spaceXApi.getAllLaunches()
        database.createLaunches(listOf(fetchedLaunches[count]))
        val newLaunches = database.getAllLaunches()
        mutableState.update {
            it.copy(
                isLoading = false,
                launches = newLaunches
            )
        }
    }

    data class State(
        val isLoading: Boolean = true,
        val launches: List<RocketLaunch> = emptyList()
    )
}