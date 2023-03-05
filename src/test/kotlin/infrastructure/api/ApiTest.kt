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
import infrastructure.database.DatabaseManager
import infrastructure.database.withMongo
import infrastructure.digitaltwins.DigitalTwinManagerTestDouble
import infrastructure.provider.ManagerProvider
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ApiTest : StringSpec({
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

    fun apiTestApplication(tests: suspend ApplicationTestBuilder.() -> Unit) {
        val apiPath = "/api/v1"
        val port = 3000
        val provider by lazy {
            object : ManagerProvider {
                private val digitalTwinManager = DigitalTwinManagerTestDouble()
                private val databaseManager = DatabaseManager("mongodb://localhost:27017")
                override val roomDigitalTwinManager = digitalTwinManager
                override val roomDatabaseManager = databaseManager
                override val medicalTechnologyDigitalTwinManager = digitalTwinManager
                override val medicalTechnologyDatabaseManager = databaseManager
            }
        }
        withMongo {
            testApplication {
                application {
                    install(ContentNegotiation) {
                        json()
                    }
                    roomAPI(apiPath, port, provider)
                    medicalTechnologyAPI(apiPath, port, provider)
                }
                tests()
            }
        }
    }

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
