/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.digitaltwins

import application.controller.manager.RoomDigitalTwinManager
import entity.zone.Room
import entity.zone.RoomID

/**
 * Implementation of the Digital Twin manager.
 */
class DigitalTwinManager : RoomDigitalTwinManager {
    init {
        checkNotNull(System.getenv(DigitalTwinManager.dtAppIdVariable)) { "azure client app id required" }
        checkNotNull(System.getenv(DigitalTwinManager.dtTenantVariable)) { "azure tenant id required" }
        checkNotNull(System.getenv(DigitalTwinManager.dtAppSecretVariable)) { "azure client secret id required" }
        checkNotNull(System.getenv(DigitalTwinManager.dtEndpointVariable)) { "azure dt endpoint required" }
    }

//    private val dtClient = DigitalTwinsClientBuilder()
//        .credential(DefaultAzureCredentialBuilder().build())
//        .endpoint(System.getenv(DigitalTwinManager.dtEndpointVariable))
//        .buildClient()

    override fun createRoomDigitalTwin(room: Room): Boolean {
        return true
    }

    override fun deleteRoomDigitalTwin(roomId: RoomID): Boolean {
        TODO("Not yet implemented")
    }

    override fun findBy(roomId: RoomID): Room? {
        TODO("Not yet implemented")
    }

    companion object {
        private const val dtAppIdVariable = "AZURE_CLIENT_ID"
        private const val dtTenantVariable = "AZURE_TENANT_ID"
        private const val dtAppSecretVariable = "AZURE_CLIENT_SECRET"
        private const val dtEndpointVariable = "AZURE_DT_ENDPOINT"
    }
}
