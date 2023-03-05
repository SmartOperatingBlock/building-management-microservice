/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.api

import infrastructure.provider.ManagerProvider
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText
import java.time.format.DateTimeParseException

/**
 * It manages the REST-API of the microservice.
 * @param[provider] the provider of managers.
 */
class APIController(private val provider: ManagerProvider) {
    /** Starts the http server to serve the client requests. */
    fun start() {
        embeddedServer(Netty, port = port) {
            dispatcher(this)
            exceptionHandler(this)
            install(ContentNegotiation) {
                json()
            }
        }.start(wait = true)
    }

    private fun dispatcher(app: Application) {
        with(app) {
            roomAPI(apiPath, port, provider)
            medicalTechnologyAPI(apiPath, port, provider)
        }
    }

    private fun exceptionHandler(app: Application) {
        with(app) {
            install(StatusPages) {
                exception<DateTimeParseException> { call, _ ->
                    call.respondText(
                        text = "Date time information must be in ISO 8601 format",
                        status = HttpStatusCode.BadRequest
                    )
                }
            }
        }
    }

    companion object {
        private const val port = 3000
        private const val apiVersion = "v1"
        private const val apiPath = "/api/$apiVersion"
    }
}
