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
import application.presenter.api.model.MedicalTechnologyEntry
import application.presenter.api.model.MedicalTechnologyPatch
import application.presenter.api.serializer.ApiSerializer.toMedicalTechnologyApiDto
import application.service.MedicalTechnologyService
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.RoomID
import infrastructure.provider.ManagerProvider
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.time.Instant

/**
 * The Medical Technology API available to handle medical technology requests.
 * @param[apiPath] it represents the path to reach the api.
 * @param[port] the port where the api are exposed.
 * @param[provider] the provider of managers.
 */
fun Application.medicalTechnologyAPI(apiPath: String, port: Int, provider: ManagerProvider) {
    routing {
        post("$apiPath/medical-technologies") {
            val medicalTechnology = call.receive<MedicalTechnologyEntry>().toMedicalTechnology()
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
            MedicalTechnologyService.GetMedicalTechnology(
                MedicalTechnologyID(call.parameters["technologyId"].orEmpty()),
                MedicalTechnologyController(
                    provider.medicalTechnologyDigitalTwinManager,
                    provider.medicalTechnologyDatabaseManager
                ),
                call.request.queryParameters["dateTime"]?.let { rawDateTime -> Instant.parse(rawDateTime) }
            ).execute().apply {
                when (this) {
                    null -> call.respond(HttpStatusCode.NotFound)
                    else -> call.respond(this.toMedicalTechnologyApiDto())
                }
            }
        }
        delete("$apiPath/medical-technologies/{technologyId}") {
            call.respond(
                MedicalTechnologyService.DeleteMedicalTechnology(
                    MedicalTechnologyID(call.parameters["technologyId"].orEmpty()),
                    MedicalTechnologyController(
                        provider.medicalTechnologyDigitalTwinManager,
                        provider.medicalTechnologyDatabaseManager
                    ),
                ).execute().let { result ->
                    if (result) HttpStatusCode.NoContent else HttpStatusCode.NotFound
                }
            )
        }
        patch("$apiPath/medical-technologies/{technologyId}") {
            call.respond(
                MedicalTechnologyService.MapMedicalTechnologyToRoom(
                    MedicalTechnologyID(call.parameters["technologyId"].orEmpty()),
                    call.receive<MedicalTechnologyPatch>().roomId?.let { roomId -> RoomID(roomId.trim()) },
                    RoomController(provider.roomDigitalTwinManager, provider.roomDatabaseManager),
                    MedicalTechnologyController(
                        provider.medicalTechnologyDigitalTwinManager,
                        provider.medicalTechnologyDatabaseManager
                    )
                ).execute().let { result ->
                    if (result) HttpStatusCode.NoContent else HttpStatusCode.NotFound
                }
            )
        }
    }
}
