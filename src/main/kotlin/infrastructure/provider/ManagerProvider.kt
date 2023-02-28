/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.provider

import application.controller.manager.MedicalTechnologyDatabaseManager
import application.controller.manager.MedicalTechnologyDigitalTwinManager
import application.controller.manager.RoomDatabaseManager
import application.controller.manager.RoomDigitalTwinManager

/**
 * Provider of managers.
 */
interface ManagerProvider {
    /** The manager of the Digital Twin for rooms. */
    val roomDigitalTwinManager: RoomDigitalTwinManager

    /** The manager of the Database for room. */
    val roomDatabaseManager: RoomDatabaseManager

    /** The manager of the Digital Twins for medical technologies. */
    val medicalTechnologyDigitalTwinManager: MedicalTechnologyDigitalTwinManager

    /** The manager of the Database for medical technologies. */
    val medicalTechnologyDatabaseManager: MedicalTechnologyDatabaseManager
}
