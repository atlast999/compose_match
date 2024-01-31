package org.company.app.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.ClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpMethod
import org.company.app.data.entity.RocketLaunch

class SpaceXApi(
    private val httpClient: HttpClient,
) {

    suspend fun getAllLaunches(): List<RocketLaunch> {
        return httpClient.get("https://api.spacexdata.com/v5/launches").body()
    }

    suspend fun createZoom(): String {
        return httpClient.post("").body()
    }

    suspend fun getWebSocketSession(): ClientWebSocketSession {
        return httpClient.webSocketSession(
            method = HttpMethod.Get,
            host = "",
            port = 8080,
            path = "/ws",
        ) {

        }
    }
//
//    fun socketReceiveFlow(): Flow<Frame> {
//        return
//    }
}