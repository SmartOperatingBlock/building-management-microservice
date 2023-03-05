/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.digitaltwins

import application.controller.manager.MedicalTechnologyDigitalTwinManager
import application.controller.manager.RoomDigitalTwinManager
import com.azure.digitaltwins.core.BasicDigitalTwin
import com.azure.digitaltwins.core.BasicRelationship
import com.azure.digitaltwins.core.DigitalTwinsClient
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import com.azure.identity.DefaultAzureCredentialBuilder
import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.Room
import entity.zone.RoomID
import infrastructure.digitaltwins.adtpresentation.MedicalTechnologyAdtPresentation
import infrastructure.digitaltwins.adtpresentation.MedicalTechnologyAdtPresentation.toDigitalTwin
import infrastructure.digitaltwins.adtpresentation.MedicalTechnologyAdtPresentation.toMedicalTechnology
import infrastructure.digitaltwins.adtpresentation.RoomAdtPresentation.toDigitalTwin
import infrastructure.digitaltwins.adtpresentation.RoomAdtPresentation.toRoom
import infrastructure.digitaltwins.query.AdtQuery
import infrastructure.digitaltwins.query.AdtQuery.Companion.AdtQueryUtils.eq
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Implementation of the Digital Twin manager.
 */
class DigitalTwinManager : RoomDigitalTwinManager, MedicalTechnologyDigitalTwinManager {
    init {
        checkNotNull(System.getenv(dtAppIdVariable)) { "azure client app id required" }
        checkNotNull(System.getenv(dtTenantVariable)) { "azure tenant id required" }
        checkNotNull(System.getenv(dtAppSecretVariable)) { "azure client secret id required" }
        checkNotNull(System.getenv(dtEndpointVariable)) { "azure dt endpoint required" }
    }

    private val dtClient = DigitalTwinsClientBuilder()
        .credential(DefaultAzureCredentialBuilder().build())
        .endpoint(System.getenv(dtEndpointVariable))
        .buildClient()

    override fun createRoomDigitalTwin(room: Room): Boolean = with(room.toDigitalTwin()) {
        dtClient.applySafeDigitalTwinOperation(false) {
            createOrReplaceDigitalTwin(this@with.id, this@with, BasicDigitalTwin::class.java)
            true
        }
    }

    override fun deleteRoomDigitalTwin(roomId: RoomID): Boolean =
        this.dtClient.applySafeDigitalTwinOperation(false) {
            deleteIncomingRelationships(roomId.value)
            deleteOutgoingRelationships(roomId.value)
            deleteDigitalTwin(roomId.value)
            true
        }

    override fun findBy(roomId: RoomID): Room? = this.dtClient.applySafeDigitalTwinOperation(null) {
        getDigitalTwin(roomId.value, BasicDigitalTwin::class.java).toRoom()
    }

    override fun createMedicalTechnologyDigitalTwin(medicalTechnology: MedicalTechnology): Boolean =
        with(medicalTechnology.toDigitalTwin()) {
            dtClient.applySafeDigitalTwinOperation(false) {
                createOrReplaceDigitalTwin(this@with.id, this@with, BasicDigitalTwin::class.java)
                true
            }
        }

    override fun deleteMedicalTechnologyDigitalTwin(medicalTechnologyId: MedicalTechnologyID): Boolean =
        this.dtClient.applySafeDigitalTwinOperation(false) {
            deleteIncomingRelationships(medicalTechnologyId.value)
            deleteOutgoingRelationships(medicalTechnologyId.value)
            deleteDigitalTwin(medicalTechnologyId.value)
            true
        }

    override fun findBy(medicalTechnologyId: MedicalTechnologyID): MedicalTechnology? =
        this.dtClient.applySafeDigitalTwinOperation(null) {
            // Get the medical technology digital twin and obtain also the room in which it is located (if present)
            getDigitalTwin(medicalTechnologyId.value, BasicDigitalTwin::class.java).toMedicalTechnology().copy(
                roomId = query(
                    AdtQuery
                        .createQuery()
                        .selectTop(1, "CT.\$dtId")
                        .fromDigitalTwins("T")
                        .joinRelationship(
                            "CT",
                            "T",
                            MedicalTechnologyAdtPresentation.IS_LOCATED_IN_OPERATING_ROOM_RELATIONSHIP
                        ).where("T.\$dtId" eq medicalTechnologyId.value).query,
                    String::class.java
                ).let {
                    if (it.count() == 1) {
                        Json.parseToJsonElement(it.first()).jsonObject["\$dtId"]?.let { id ->
                            RoomID(id.jsonPrimitive.content)
                        }
                    } else null
                }
            )
        }

    override fun mapTo(medicalTechnologyId: MedicalTechnologyID, roomId: RoomID?): Boolean {
        // if the roomId is null then the relationship must be deleted
        // remember: the relationship is room -> medical technology)
        TODO("Not yet implemented")
    }

    private fun DigitalTwinsClient.deleteIncomingRelationships(sourceId: String) {
        this.listIncomingRelationships(sourceId).forEach {
            this.deleteRelationship(it.sourceId, it.relationshipId)
        }
    }

    private fun DigitalTwinsClient.deleteOutgoingRelationships(sourceId: String, relationshipName: String? = null) {
        this.listRelationships(sourceId, BasicRelationship::class.java).forEach {
            if (relationshipName == null || it.name == relationshipName) {
                this.deleteRelationship(it.sourceId, it.id)
            }
        }
    }

    private fun <R> DigitalTwinsClient.applySafeDigitalTwinOperation(
        defaultResult: R,
        operation: DigitalTwinsClient.() -> R
    ): R =
        try {
            operation()
        } catch (exception: ErrorResponseException) {
            println(exception)
            defaultResult
        }

    companion object {
        private const val dtAppIdVariable = "AZURE_CLIENT_ID"
        private const val dtTenantVariable = "AZURE_TENANT_ID"
        private const val dtAppSecretVariable = "AZURE_CLIENT_SECRET"
        private const val dtEndpointVariable = "AZURE_DT_ENDPOINT"
    }
}
