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
 * This interface models the manager of the database for medical technologies.
 * In this way it is independent respect to the specific infrastructure technology that is used for the database,
 * having the only semantic dependency on the concept of database.
 */
interface MedicalTechnologyDatabaseManager {
    /**
     * Store a [medicalTechnology] inside the Database.
     * @return true if successfully stored, false otherwise.
     */
    fun saveMedicalTechnology(medicalTechnology: MedicalTechnology): Boolean

    /**
     * Delete a medical technology identified by [medicalTechnologyId] from the Database.
     * @return true if successfully deleted, false otherwise.
     */
    fun deleteMedicalTechnology(medicalTechnologyId: MedicalTechnologyID)

    /**
     * Map a medical technology, identified by its [medicalTechnologyId], to a room
     * identified by its [roomId].
     * @return true if successfully mapped, false otherwise
     */
    fun mapTo(medicalTechnologyId: MedicalTechnologyID, roomId: RoomID): Boolean
}
