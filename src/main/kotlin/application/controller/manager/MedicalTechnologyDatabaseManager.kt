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
import java.time.Instant

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
    fun deleteMedicalTechnology(medicalTechnologyId: MedicalTechnologyID): Boolean

    /**
     * Get a medical technology identified by its [medicalTechnologyId] from the DB.
     * Specify a past [dateTime] in order to get historical data.
     * @return null if data is not available, the medical technology otherwise.
     */
    fun findBy(medicalTechnologyId: MedicalTechnologyID, dateTime: Instant): MedicalTechnology?

    /**
     * Map a medical technology, identified by its [medicalTechnologyId], to a room
     * identified by its [roomId].
     * @return true if successfully mapped, false otherwise
     */
    fun mapTo(medicalTechnologyId: MedicalTechnologyID, roomId: RoomID): Boolean

    /**
     * Update the status of [usage] in a specified [dateTime] of a medical technology
     * identified by its [medicalTechnologyId].
     */
    fun updateMedicalTechnologyUsage(
        medicalTechnologyId: MedicalTechnologyID,
        usage: Boolean,
        dateTime: Instant
    ): Boolean
}
