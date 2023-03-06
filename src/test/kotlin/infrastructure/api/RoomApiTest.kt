/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.api

import application.presenter.api.model.EnvironmentalDataApiDto
import application.presenter.api.model.RoomApiDto
import application.presenter.api.model.RoomApiDtoType
import application.presenter.api.model.RoomEntry
import application.presenter.api.model.ValueWithUnit
import infrastructure.api.KtorTestingUtility.apiTestApplication
import infrastructure.api.util.ApiResponses
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
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

class RoomApiTest : StringSpec({
    val roomApiDto = RoomApiDto(
        "r1",
        "name",
        "z1",
        RoomApiDtoType.OPERATING_ROOM,
        EnvironmentalDataApiDto(
            ValueWithUnit(33.0, "CELSIUS"),
            55.0,
            ValueWithUnit(150.0, "LUX"),
            true
        )
    )

    suspend fun ApplicationTestBuilder.insertRoom(room: RoomApiDto) =
        client.post("/api/v1/rooms") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Json.encodeToString(room))
        }

    "I should be able to request the creation of a room" {
        apiTestApplication {
            val response = insertRoom(roomApiDto)
            response shouldHaveStatus HttpStatusCode.Created
        }
    }

    "When there is a request to add an existent room then the service should respond with a conflict status code" {
        apiTestApplication {
            insertRoom(roomApiDto)
            val response = insertRoom(roomApiDto)
            response shouldHaveStatus HttpStatusCode.Conflict
        }
    }

    "It should be possible to obtain an existing room" {
        apiTestApplication {
            insertRoom(roomApiDto)
            val response = client.get("/api/v1/rooms/${roomApiDto.id}")
            response shouldHaveStatus HttpStatusCode.OK
            Json.decodeFromString<RoomApiDto>(response.bodyAsText()) shouldBe roomApiDto
        }
    }

    "It should be possible to obtain historical data about a room" {
        apiTestApplication {
            insertRoom(roomApiDto)
            val response = client.get("/api/v1/rooms/${roomApiDto.id}?dateTime=${Instant.now()}")
            response shouldHaveStatus HttpStatusCode.OK
        }
    }

    "It should handle the get request on a non existing room" {
        apiTestApplication {
            client.get("/api/v1/rooms/${roomApiDto.id}") shouldHaveStatus HttpStatusCode.NotFound
        }
    }

    "It should be able to get all the room entries" {
        apiTestApplication {
            insertRoom(roomApiDto)
            val response = client.get("/api/v1/rooms")
            response shouldHaveStatus HttpStatusCode.OK
            Json.decodeFromString<ApiResponses.ResponseEntryList<ApiResponses.ResponseEntry<RoomEntry>>>(
                response.bodyAsText()
            ).total shouldBe 1
        }
    }

    "It should be able to get all the room entries, but when there aren't anyone should return no content status" {
        apiTestApplication {
            val response = client.get("/api/v1/rooms")
            response shouldHaveStatus HttpStatusCode.NoContent
            Json.decodeFromString<ApiResponses.ResponseEntryList<ApiResponses.ResponseEntry<RoomEntry>>>(
                response.bodyAsText()
            ).total shouldBe 0
        }
    }

    "It should be possible to delete an existing room" {
        apiTestApplication {
            insertRoom(roomApiDto)
            client.delete("/api/v1/rooms/${roomApiDto.id}") shouldHaveStatus HttpStatusCode.NoContent
        }
    }

    "It should not be possible to delete a not-existent room" {
        apiTestApplication {
            client.delete("/api/v1/rooms/${roomApiDto.id}") shouldHaveStatus HttpStatusCode.NotFound
        }
    }
})
