/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.api

import application.controller.MedicalTechnologyController
import application.controller.RoomController
import application.presenter.api.deserializer.ApiDeserializer.toMedicalTechnology
import application.presenter.api.deserializer.ApiDeserializer.toRoom
import application.presenter.api.model.MedicalTechnologyApiDto
import application.presenter.api.model.RoomApiDto
import application.presenter.api.serializer.ApiSerializer.toMedicalTechnologyApiDto
import application.presenter.api.serializer.ApiSerializer.toRoomApiDto
import application.service.MedicalTechnologyService
import application.service.RoomService
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.RoomID
import infrastructure.api.util.ApiResponses
import infrastructure.provider.ManagerProvider
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.receive
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.time.Instant
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
            roomAPI(this)
            medicalTechnologyAPI(this)
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

    /**
     * The Room API available to handle room requests.
     * @param[app] it represents the running ktor web application
     */
    private fun roomAPI(app: Application) {
        with(app) {
            routing {
                post("$apiPath/rooms") {
                    val room = call.receive<RoomApiDto>().toRoom()
                    RoomService.CreateRoom(
                        room,
                        RoomController(provider.roomDigitalTwinManager, provider.roomDatabaseManager)
                    ).execute().apply {
                        when (this) {
                            null -> call.respond(HttpStatusCode.Conflict)
                            else -> {
                                call.response.header(
                                    HttpHeaders.Location,
                                    "http://localhost:$port$apiPath/rooms/${room.id.value}"
                                )
                                call.respond(HttpStatusCode.Created)
                            }
                        }
                    }
                }
                get("$apiPath/rooms") {
                    val entries = RoomService.GetAllRoomEntry(
                        RoomController(provider.roomDigitalTwinManager, provider.roomDatabaseManager)
                    ).execute().map { entry ->
                        ApiResponses.ResponseEntry(entry, "http://localhost:$port$apiPath/rooms/${entry.id}")
                    }
                    call.response.status(if (entries.isNotEmpty()) HttpStatusCode.OK else HttpStatusCode.NoContent)
                    call.respond(ApiResponses.ResponseEntryList(entries))
                }
                get("$apiPath/rooms/{roomId}") {
                    call.respond(
                        RoomService.GetRoom(
                            RoomID(call.parameters["roomId"].orEmpty()),
                            RoomController(provider.roomDigitalTwinManager, provider.roomDatabaseManager),
                            call.request.queryParameters["dateTime"]?.let { rawDateTime -> Instant.parse(rawDateTime) }
                        ).execute().let { room -> room?.toRoomApiDto() ?: HttpStatusCode.NotFound }
                    )
                }
                delete("$apiPath/rooms/{roomId}") {
                    call.respond(
                        RoomService.DeleteRoom(
                            RoomID(call.parameters["roomId"].orEmpty()),
                            RoomController(provider.roomDigitalTwinManager, provider.roomDatabaseManager)
                        ).execute().let { result ->
                            if (result) HttpStatusCode.NoContent else HttpStatusCode.NotFound
                        }
                    )
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
                    val medicalTechnology = call.receive<MedicalTechnologyApiDto>().toMedicalTechnology()
                    MedicalTechnologyService.CreateMedicalTechnology(
                        medicalTechnology,
                        MedicalTechnologyController(
                            provider.medicalTechnologyDigitalTwinManager,
                            provider.medicalTechnologyDatabaseManager
                        )
                    ).execute().apply {
                        when (this) {
                            null -> call.respond(HttpStatusCode.Conflict)
                            else -> {
                                call.response.header(
                                    HttpHeaders.Location,
                                    "http://localhost:$port$apiPath/medical-technologies/${medicalTechnology.id.value}"
                                )
                                call.respond(HttpStatusCode.Created)
                            }
                        }
                    }
                }
                get("$apiPath/medical-technologies/{technologyId}") {
                    call.respond(
                        MedicalTechnologyService.GetMedicalTechnology(
                            MedicalTechnologyID(call.parameters["technologyId"].orEmpty()),
                            MedicalTechnologyController(
                                provider.medicalTechnologyDigitalTwinManager,
                                provider.medicalTechnologyDatabaseManager
                            ),
                            call.request.queryParameters["dateTime"]?.let { rawDateTime -> Instant.parse(rawDateTime) }
                        ).execute().let { medicalTechnology ->
                            medicalTechnology?.toMedicalTechnologyApiDto() ?: HttpStatusCode.NotFound
                        }
                    )
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
        private const val port = 3000
        private const val apiVersion = "v1"
        private const val apiPath = "/api/$apiVersion"
    }
}
