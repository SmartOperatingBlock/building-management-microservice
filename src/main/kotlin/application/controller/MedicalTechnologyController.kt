/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.controller

import application.controller.manager.MedicalTechnologyDatabaseManager
import application.controller.manager.MedicalTechnologyDigitalTwinManager
import application.controller.util.rollback
import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.RoomID
import usecase.repository.MedicalTechnologyRepository
import java.time.Instant

/**
 * Implementation of medical technology repository that handle the application logic
 * using both db and digital twin.
 * @param[digitalTwinManager] the digital twin manager for medical technologies.
 * @param[databaseManager] the database manager for medical technologies.
 */
class MedicalTechnologyController(
    private val digitalTwinManager: MedicalTechnologyDigitalTwinManager,
    private val databaseManager: MedicalTechnologyDatabaseManager
) : MedicalTechnologyRepository {
    override fun createMedicalTechnology(medicalTechnology: MedicalTechnology): MedicalTechnology? = (
        this.digitalTwinManager.createMedicalTechnologyDigitalTwin(medicalTechnology) &&
            this.databaseManager.saveMedicalTechnology(medicalTechnology).rollback {
                this.digitalTwinManager.deleteMedicalTechnologyDigitalTwin(medicalTechnology.id)
            }
        ).let { if (it) medicalTechnology else null }

    override fun deleteMedicalTechnology(medicalTechnologyId: MedicalTechnologyID): Boolean {
        TODO("Not yet implemented")
    }

    override fun findBy(medicalTechnologyId: MedicalTechnologyID, dateTime: Instant?): MedicalTechnology? =
        if (dateTime == null) { // if the date-time is null, then obtain present information
            this.digitalTwinManager.findBy(medicalTechnologyId)
        } else {
            this.databaseManager.findBy(medicalTechnologyId, dateTime)
        }

    override fun mapTechnologyTo(medicalTechnologyId: MedicalTechnologyID, roomId: RoomID): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateMedicalTechnologyUsage(
        medicalTechnologyId: MedicalTechnologyID,
        usage: Boolean,
        dateTime: Instant,
    ): Boolean {
        TODO("Not yet implemented")
    }
}
