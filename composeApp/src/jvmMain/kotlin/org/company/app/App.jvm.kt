package org.company.app

import org.company.app.cache.DatabaseDriverFactory

internal actual fun provideDatabaseDriverFactory(): DatabaseDriverFactory {
    return DatabaseDriverFactory()
}