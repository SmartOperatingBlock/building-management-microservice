/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.api

import application.controller.RoomController
import application.presenter.api.deserializer.ApiDeserializer.toRoom
import application.presenter.api.model.RoomApiDto
import application.service.Service
import infrastructure.provider.ManagerProvider
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

/**
 * It manages the REST-API of the microservice.
 * @param[provider] the provider of managers.
 */
class APIController(private val provider: ManagerProvider) {
    /**
     * Starts the http server to serve the client requests.
     */
    fun start() {
        embeddedServer(Netty, port = 3000) {
            dispatcher(this)
            install(ContentNegotiation) {
                json()
            }
        }.start(wait = true)
    }

    /**
     * Dispatcher of the http routing.
     * Needs to be public due to reflection used by ktor.
     */
    fun dispatcher(app: Application) {
        with(app) {
            roomAPI(this)
            medicalTechnologyAPI(this)
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
                    // nel body ha la room in json
                    // deserializzo in Room
                    /* logica:
                        0. caso d'uso esiste la room
                            - controlla se non esiste un'altra room uguale
                        1. Salvo la room nel db
                        2. creo il digital twin su Azure Digital Twins
                     */
                    val room = call.receive<RoomApiDto>().toRoom()
                    Service.CreateRoom(room, RoomController(provider.roomDigitalTwinManager))
                    call.respondText("[${Thread.currentThread().name}] Room POST! \n$room")
                }
                get("$apiPath/rooms/{roomId}") {
                    call.respondText("[${Thread.currentThread().name}] Room GET!")
                }
                delete("$apiPath/rooms/{roomId}") {
                    call.respondText("[${Thread.currentThread().name}] Room DELETE!")
                }
            }
        }
    }

    /**
     * The Medical Technology API available to handle medical technology requests.
     * @param[app] it represents the running ktor web application
     */
    private fun medicalTechnologyAPI(app: Application) {
        with(app) {
            routing {
                post("$apiPath/medical-technologies") {
                    call.respondText("[${Thread.currentThread().name}] Medical Technology POST!")
                }
                get("$apiPath/medical-technologies/{technologyId}") {
                    call.respondText("[${Thread.currentThread().name}] Medical Technology GET!")
                }
                delete("$apiPath/medical-technologies/{technologyId}") {
                    call.respondText("[${Thread.currentThread().name}] Medical Technology DELETE!")
                }
                patch("$apiPath/medical-technologies/{technologyId}") {
                    call.respondText("[${Thread.currentThread().name}] Medical Technology PATCH!")
                }
            }
        }
    }

    companion object {
        private const val apiVersion = "v1"
        private const val apiPath = "/api/$apiVersion"
    }
}
