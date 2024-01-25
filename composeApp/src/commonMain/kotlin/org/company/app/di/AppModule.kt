package org.company.app.di

import org.company.app.presentation.authentication.login.LoginScreenModel
import org.koin.dsl.module

val loginModule = module {
    factory { LoginScreenModel() }
}

internal fun appModules() = listOf(
    loginModule,
)