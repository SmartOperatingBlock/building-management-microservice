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
import infrastructure.api.KtorTestingUtility.apiTestApplication
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldNotBe
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.ApplicationTestBuilder
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MedicalTechnologyApiTest : StringSpec({
    val medicalTechnologyApiDto = MedicalTechnologyApiDto(
        id = "mt-1",
        name = "name",
        description = "description",
        type = MedicalTechnologyApiDtoType.ENDOSCOPE,
        inUse = true,
        roomId = "r-1"
    )

    suspend fun ApplicationTestBuilder.insertMedicalTechnology(medicalTechnology: MedicalTechnologyApiDto) =
        client.post("/api/v1/medical-technologies") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Json.encodeToString(medicalTechnology))
        }

    "I should be able to request the creation of a medical technology" {
        apiTestApplication {
            val response = insertMedicalTechnology(medicalTechnologyApiDto)
            response shouldHaveStatus HttpStatusCode.Created
            response.headers[HttpHeaders.Location] shouldNotBe null
        }
    }

    "Add an existent medical technology then the service should result in a conflict status code" {
        apiTestApplication {
            insertMedicalTechnology(medicalTechnologyApiDto)
            val response = insertMedicalTechnology(medicalTechnologyApiDto)
            response shouldHaveStatus HttpStatusCode.Conflict
        }
    }
})
