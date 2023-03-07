/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.api

import application.presenter.api.model.MedicalTechnologyApiDto
import application.presenter.api.model.MedicalTechnologyApiDtoType
import application.presenter.api.model.MedicalTechnologyEntry
import application.presenter.api.model.MedicalTechnologyPatch
import application.presenter.api.model.RoomApiDtoType
import application.presenter.api.model.RoomEntry
import infrastructure.api.KtorTestingUtility.apiTestApplication
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.ApplicationTestBuilder
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant

class MedicalTechnologyApiTest : StringSpec({
    val medicalTechnologyEntry = MedicalTechnologyEntry(
        id = "mt-1",
        name = "name",
        description = "description",
        type = MedicalTechnologyApiDtoType.ENDOSCOPE
    )

    val medicalTechnologyApiDto = MedicalTechnologyApiDto(
        id = "mt-1",
        name = "name",
        description = "description",
        type = MedicalTechnologyApiDtoType.ENDOSCOPE,
        inUse = false,
        roomId = null
    )

    suspend fun ApplicationTestBuilder.insertMedicalTechnology(medicalTechnology: MedicalTechnologyEntry) =
        client.post("/api/v1/medical-technologies") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Json.encodeToString(medicalTechnology))
        }

    "I should be able to request the creation of a medical technology" {
        apiTestApplication {
            val response = insertMedicalTechnology(medicalTechnologyEntry)
            response shouldHaveStatus HttpStatusCode.Created
            response.headers[HttpHeaders.Location] shouldNotBe null
        }
    }

    "Add an existent medical technology then the service should result in a conflict status code" {
        apiTestApplication {
            insertMedicalTechnology(medicalTechnologyEntry)
            val response = insertMedicalTechnology(medicalTechnologyEntry)
            response shouldHaveStatus HttpStatusCode.Conflict
        }
    }

    "It should be possible to obtain an existing medical technology" {
        apiTestApplication {
            insertMedicalTechnology(medicalTechnologyEntry)
            val response = client.get("/api/v1/medical-technologies/${medicalTechnologyEntry.id}")
            response shouldHaveStatus HttpStatusCode.OK
            Json.decodeFromString<MedicalTechnologyApiDto>(response.bodyAsText()) shouldBe medicalTechnologyApiDto
        }
    }

    "It should be possible to obtain historical data about a medical technology" {
        apiTestApplication {
            insertMedicalTechnology(medicalTechnologyEntry)
            val response = client
                .get("/api/v1/medical-technologies/${medicalTechnologyEntry.id}?dateTime=${Instant.now()}")
            response shouldHaveStatus HttpStatusCode.OK
        }
    }

    "It should handle the get request on a non existing medical technology" {
        apiTestApplication {
            val response = client.get("/api/v1/medical-technologies/${medicalTechnologyEntry.id}")
            response shouldHaveStatus HttpStatusCode.NotFound
        }
    }

    "It should be possible to delete an existing medical technology" {
        apiTestApplication {
            insertMedicalTechnology(medicalTechnologyEntry)
            val response = client.delete("/api/v1/medical-technologies/${medicalTechnologyEntry.id}")
            response shouldHaveStatus HttpStatusCode.NoContent
        }
    }

    "It should not be possible to delete a not-existent medical technology" {
        apiTestApplication {
            val response = client.delete("/api/v1/medical-technologies/${medicalTechnologyEntry.id}")
            response shouldHaveStatus HttpStatusCode.NotFound
        }
    }

    "It should be possible to update the mapping of a medical technology" {
        apiTestApplication {
            // create room
            client.post("/api/v1/rooms") {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Json.encodeToString(
                        RoomEntry(
                            "r1",
                            "name",
                            "z1",
                            RoomApiDtoType.OPERATING_ROOM
                        )
                    )
                )
            }
            // create medical technology
            insertMedicalTechnology(medicalTechnologyEntry)
            val response = client.patch("/api/v1/medical-technologies/${medicalTechnologyEntry.id}") {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(MedicalTechnologyPatch("r1")))
            }
            response shouldHaveStatus HttpStatusCode.NoContent
        }
    }

    "It should not be possible to map a non existent medical technology to a non existent room" {
        apiTestApplication {
            val response = client.patch("/api/v1/medical-technologies/${medicalTechnologyEntry.id}") {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(MedicalTechnologyPatch("r1")))
            }
            response shouldHaveStatus HttpStatusCode.NotFound
        }
    }

    "It should not be possible to map an existent medical technology to a non existent room" {
        apiTestApplication {
            insertMedicalTechnology(medicalTechnologyEntry)
            val response = client.patch("/api/v1/medical-technologies/${medicalTechnologyEntry.id}") {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(MedicalTechnologyPatch("r1")))
            }
            response shouldHaveStatus HttpStatusCode.NotFound
        }
    }
})
