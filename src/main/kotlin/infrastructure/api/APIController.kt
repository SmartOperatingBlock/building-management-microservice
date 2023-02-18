/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.api

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

/**
 * It manages the REST-API of the microservice.
 */
class APIController {
    /**
     * Starts the http server to serve the client requests.
     */
    fun start() {
        embeddedServer(Netty, port = 3000, module = this::dispatcher).start(wait = true)
    }

    /**
     * Dispatcher of the http routing.
     * Needs to be public due to reflection used by ktor.
     */
    fun dispatcher(app: Application) {
        with(app) {
            roomAPI(this)
            medicalTechnologyPI(this)
        }
    }

    /**
     * The Room API available to handle room requests.
     * @param[app] it represents the running ktor web application
     */
    private fun roomAPI(app: Application) {
        with(app) {
            routing {
                post("$apiPath/rooms") {
                    call.respondText("[${Thread.currentThread().name}] Room POST!")
                }
                get("$apiPath/rooms/{room-id}") {
                    call.respondText("[${Thread.currentThread().name}] Room GET!")
                }
                delete("$apiPath/rooms/{room-id}") {
                    call.respondText("[${Thread.currentThread().name}] Room DELETE!")
                }
            }
        }
    }

    /**
     * The Medical Technology API available to handle medical technology requests.
     * @param[app] it represents the running ktor web application
     */
    private fun medicalTechnologyPI(app: Application) {
        with(app) {
            routing {
                post("$apiPath/medicalTechnologies") {
                    call.respondText("[${Thread.currentThread().name}] Medical Technology POST!")
                }
                get("$apiPath/medicalTechnologies/{technology-id}") {
                    call.respondText("[${Thread.currentThread().name}] Medical Technology GET!")
                }
                delete("$apiPath/medicalTechnologies/{technology-id}") {
                    call.respondText("[${Thread.currentThread().name}] Medical Technology DELETE!")
                }
                patch("$apiPath/medicalTechnologies/{technology-id}") {
                    call.respondText("[${Thread.currentThread().name}] Medical Technology PATCH!")
                }
            }
        }
    }

    companion object {
        private const val apiPath = "/api"
    }
}
