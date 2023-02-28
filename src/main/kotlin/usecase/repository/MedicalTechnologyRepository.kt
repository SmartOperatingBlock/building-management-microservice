/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package usecase.repository

import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.RoomID
import java.time.Instant

/**
 * Interface that models the repository to manage Medical Technologies.
 */
interface MedicalTechnologyRepository {
    /**
     * Create a [medicalTechnology].
     * @return null if the medical technology already exists, the  medical technology created otherwise.
     */
    fun createMedicalTechnology(medicalTechnology: MedicalTechnology): MedicalTechnology?

    /**
     * Delete a medical technology by its [medicalTechnologyId].
     * @return true if correctly deleted, false otherwise.
     */
    fun deleteMedicalTechnology(medicalTechnologyId: MedicalTechnologyID): Boolean

    /**
     * Find a medical technology by its [medicalTechnologyId] get its data in a specific [dateTime] if specified.
     * @return the medical technology if present, null otherwise.
     */
    fun findBy(medicalTechnologyId: MedicalTechnologyID, dateTime: Instant?): MedicalTechnology?

    /**
     * Map a medical technology identified by its [medicalTechnologyId] to another [roomId].
     * NB: the roomId existence isn't checked. You need to check its existence on your own.
     * @return false if the medical technology does not exist, true otherwise.
     */
    fun mapTechnologyTo(medicalTechnologyId: MedicalTechnologyID, roomId: RoomID): Boolean
}
