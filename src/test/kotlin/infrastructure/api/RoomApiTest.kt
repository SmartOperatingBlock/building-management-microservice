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
import application.presenter.api.model.ValueWithUnit
import infrastructure.api.KtorTestingUtility.apiTestApplication
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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

    "I should be able to request the creation of a room" {
        apiTestApplication {
            val response = client.post("/api/v1/rooms") {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(roomApiDto))
            }
            response shouldHaveStatus HttpStatusCode.Created
        }
    }
})
