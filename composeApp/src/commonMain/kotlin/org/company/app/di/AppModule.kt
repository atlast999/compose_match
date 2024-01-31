package org.company.app.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.company.app.cache.Database
import org.company.app.data.repository.FakeChatRepositoryImpl
import org.company.app.domain.repository.ChatRepository
import org.company.app.network.SpaceXApi
import org.company.app.presentation.authentication.login.LoginScreenModel
import org.company.app.presentation.chat.ChatScreenModel
import org.company.app.presentation.chat.room.ChatRoomScreenModel
import org.company.app.presentation.home.HomeScreenModel
import org.koin.dsl.module

val apiModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }
            install(Logging) {
                level = LogLevel.BODY
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v(message, null, "HTTP Client")
                    }
                }
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 2)
                exponentialDelay()
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 30_000
                socketTimeoutMillis = 60_000
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens("", "")
                    }
                    refreshTokens {
//                        this.client
                        BearerTokens("", "")
                    }
                    sendWithoutRequest { true }
                }
            }
            install(HttpCache)
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
        }.also { Napier.base(DebugAntilog()) }
    }
    single {
        SpaceXApi(httpClient = get())
    }
}

val databaseModule = module {
    single {
        Database(
            databaseDriverFactory = get()
        )
    }
}

val repositoryModule = module {
    single<ChatRepository> {
        FakeChatRepositoryImpl()
    }
}

val viewModelModule = module {
    factory { LoginScreenModel() }
    factory {
        HomeScreenModel(
            spaceXApi = get(),
            database = get(),
        )
    }

    factory {
        ChatScreenModel(spaceXApi = get())
    }
    factory {
        ChatRoomScreenModel(chatRepository = get())
    }
}

internal fun appModules() = listOf(
    platformModule,
    apiModule,
    databaseModule,
    repositoryModule,
    viewModelModule,
)