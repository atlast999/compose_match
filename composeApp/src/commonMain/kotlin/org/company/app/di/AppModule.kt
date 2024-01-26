package org.company.app.di

import org.company.app.cache.Database
import org.company.app.network.SpaceXApi
import org.company.app.presentation.authentication.login.LoginScreenModel
import org.company.app.presentation.home.HomeScreenModel
import org.koin.dsl.module

val apiModule = module {
    single {
        SpaceXApi()
    }
}

val databaseModule = module {
    single {
        Database(
            databaseDriverFactory = get()
        )
    }
}

val viewModelModule = module {
    factory { LoginScreenModel() }
    factory { HomeScreenModel(
        spaceXApi = get(),
        database = get(),
    ) }
}

internal fun appModules() = listOf(
    platformModule,
    apiModule,
    databaseModule,
    viewModelModule,
)