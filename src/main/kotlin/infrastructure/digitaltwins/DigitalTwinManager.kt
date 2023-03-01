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
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import com.azure.identity.DefaultAzureCredentialBuilder
import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.Room
import entity.zone.RoomID
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

    override fun createRoomDigitalTwin(room: Room): Boolean {
        with(room.toDigitalTwin()) {
            try {
                dtClient.createOrReplaceDigitalTwin(this.id, this, BasicDigitalTwin::class.java)
                return true
            } catch (e: ErrorResponseException) {
                println(e) // log the exception.
                return false
            }
        }
    }

    override fun deleteRoomDigitalTwin(roomId: RoomID): Boolean {
        fun deleteIncomingRelationships() {
            this.dtClient.listIncomingRelationships(roomId.value).forEach {
                this.dtClient.deleteRelationship(it.sourceId, it.relationshipId)
            }
        }

        fun deleteOutgoingRelationships() {
            this.dtClient.listRelationships(roomId.value, BasicRelationship::class.java).forEach {
                this.dtClient.deleteRelationship(it.sourceId, it.id)
            }
        }

        return try {
            deleteIncomingRelationships()
            deleteOutgoingRelationships()
            dtClient.deleteDigitalTwin(roomId.value)
            true
        } catch (e: ErrorResponseException) {
            println(e) // log the exception.
            false
        }
    }

    override fun findBy(roomId: RoomID): Room? =
        try {
            this.dtClient.getDigitalTwin(roomId.value, BasicDigitalTwin::class.java).toRoom()
        } catch (e: ErrorResponseException) {
            println(e) // log the exception.
            null
        }

    override fun createMedicalTechnologyDigitalTwin(medicalTechnology: MedicalTechnology): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteMedicalTechnologyDigitalTwin(medicalTechnologyId: MedicalTechnologyID): Boolean {
        TODO("Not yet implemented")
    }

    override fun findBy(medicalTechnologyId: MedicalTechnologyID): MedicalTechnology? {
        TODO("Not yet implemented")
    }

    override fun mapTo(medicalTechnologyId: MedicalTechnologyID, roomId: RoomID?): Boolean {
        TODO("Not yet implemented")
    }

    companion object {
        private const val dtAppIdVariable = "AZURE_CLIENT_ID"
        private const val dtTenantVariable = "AZURE_TENANT_ID"
        private const val dtAppSecretVariable = "AZURE_CLIENT_SECRET"
        private const val dtEndpointVariable = "AZURE_DT_ENDPOINT"
    }
}
