/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.controller.manager

import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.RoomID

/**
 * This interface models the manager of Digital Twins for medical technologies.
 * In this way it is independent respect to the specific technology that is used,
 * having the only semantic dependency on the concept of Digital Twin.
 */
interface MedicalTechnologyDigitalTwinManager {
    /**
     * Create the Digital Twin of the [medicalTechnology].
     * @return true if successfully created, false otherwise.
     */
    fun createMedicalTechnologyDigitalTwin(medicalTechnology: MedicalTechnology): Boolean

    /**
     * Delete the Digital Twin of the medical technology identified by [medicalTechnologyId].
     * @return true if successfully deleted, false otherwise.
     */
    fun deleteMedicalTechnologyDigitalTwin(medicalTechnologyId: MedicalTechnologyID): Boolean

    /**
     * Get the Medical Technology Digital Twin data of the medical technology
     * identified by [medicalTechnologyId].
     * @return null if the medical technology is not present, the medical technology otherwise.
     */
    fun findBy(medicalTechnologyId: MedicalTechnologyID): MedicalTechnology?

    /**
     * Map a medical technology Digital Twin, identified by its [medicalTechnologyId], to a room
     * Digital Twin identified by its [roomId].
     * @return true if successfully mapped, false otherwise
     */
    fun mapTo(medicalTechnologyId: MedicalTechnologyID, roomId: RoomID): Boolean
}
