package org.company.app.di

import org.company.app.AndroidApp
import org.company.app.cache.DatabaseDriverFactory
import org.koin.dsl.module

actual val platformModule = module {
    single {
        DatabaseDriverFactory(
            context = AndroidApp.INSTANCE
        )
    }
}