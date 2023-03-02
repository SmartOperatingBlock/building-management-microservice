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
import infrastructure.digitaltwins.adtpresentation.MedicalTechnologyAdtPresentation.toDigitalTwin
import infrastructure.digitaltwins.adtpresentation.RoomAdtPresentation.toDigitalTwin
import infrastructure.digitaltwins.adtpresentation.RoomAdtPresentation.toRoom

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
            // val medicalTechnology =
            //    getDigitalTwin(medicalTechnologyId.value, BasicDigitalTwin::class.java).toMedicalTechnology()
            // the relationship is room -> medical technology, so it is needed a query on
            // Azure in order to get the first room that has a relation with this medical technology if exists
            // If it doesn't exist (so we need to be able to check the result count) then it's ok to have null
            // in roomId property of the medical technology, otherwise set to the returned id.
            TODO("complete this method")
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

    private fun DigitalTwinsClient.deleteOutgoingRelationships(sourceId: String) {
        this.listRelationships(sourceId, BasicRelationship::class.java).forEach {
            this.deleteRelationship(it.sourceId, it.id)
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
