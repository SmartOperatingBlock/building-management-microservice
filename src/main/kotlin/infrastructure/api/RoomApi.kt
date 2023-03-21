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
import application.presenter.api.model.RoomEntry
import application.presenter.api.serializer.ApiSerializer.toRoomApiDto
import application.service.RoomService
import entity.zone.RoomID
import infrastructure.api.util.ApiResponses
import infrastructure.provider.ManagerProvider
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.time.Instant

/**
 * The Room API available to handle room requests.
 * @param[apiPath] it represents the path to reach the api.
 * @param[port] the port where the api are exposed.
 * @param[provider] the provider of managers.
 */
fun Application.roomAPI(apiPath: String, port: Int, provider: ManagerProvider) {
    routing {
        createRoom(apiPath, port, provider)
        getAllRooms(apiPath, port, provider)
        getRoom(apiPath, provider)
        deleteRoom(apiPath, provider)
        getHistoricalRoomEnvironmentData(apiPath, provider)
    }
}

private fun Route.createRoom(apiPath: String, port: Int, provider: ManagerProvider) =
    post("$apiPath/rooms") {
        val room = call.receive<RoomEntry>().toRoom()
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

private fun Route.getAllRooms(apiPath: String, port: Int, provider: ManagerProvider) =
    get("$apiPath/rooms") {
        val entries = RoomService.GetAllRoomEntry(
            RoomController(provider.roomDigitalTwinManager, provider.roomDatabaseManager)
        ).execute().map { entry ->
            ApiResponses.ResponseEntry(entry, "http://localhost:$port$apiPath/rooms/${entry.id}")
        }
        call.response.status(if (entries.isNotEmpty()) HttpStatusCode.OK else HttpStatusCode.NoContent)
        call.respond(ApiResponses.ResponseEntryList(entries))
    }

private fun Route.getRoom(apiPath: String, provider: ManagerProvider) =
    get("$apiPath/rooms/{roomId}") {
        RoomService.GetRoom(
            RoomID(call.parameters["roomId"].orEmpty()),
            RoomController(provider.roomDigitalTwinManager, provider.roomDatabaseManager),
            call.request.queryParameters["dateTime"]?.let { rawDateTime -> Instant.parse(rawDateTime) }
        ).execute().apply {
            when (this) {
                null -> call.respond(HttpStatusCode.NotFound)
                else -> call.respond(this.toRoomApiDto())
            }
        }
    }

private fun Route.deleteRoom(apiPath: String, provider: ManagerProvider) =
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

private fun Route.getHistoricalRoomEnvironmentData(apiPath: String, provider: ManagerProvider) =
    get("$apiPath/rooms/data/{roomId}") {
        RoomService.ExportRoomEnvironmentalData(
            RoomID(call.parameters["roomId"].orEmpty()),
            RoomController(provider.roomDigitalTwinManager, provider.roomDatabaseManager),
            call.request.queryParameters["from"]?.let { rawDateTime -> Instant.parse(rawDateTime) }
                ?: Instant.now(),
            call.request.queryParameters["to"]?.let { rawDateTime -> Instant.parse(rawDateTime) }
        ).execute()?.map {
            ApiResponses.ResponseTimedEntry(it.second, it.first.toString())
        }.apply {
            when (this) {
                null -> call.respond(HttpStatusCode.NotFound)
                else -> call.respond(ApiResponses.ResponseEntryList(this))
            }
        }
    }
