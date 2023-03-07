/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.service

import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.RoomID
import usecase.repository.MedicalTechnologyRepository
import usecase.repository.RoomRepository
import java.time.Instant

/**
 * Module that wraps all the services that orchestrate the application logic (not domain one).
 */
object MedicalTechnologyService {
    /**
     * Application Service that has the objective of creating a [medicalTechnology] using
     * the provided [medicalTechnologyRepository].
     */
    class CreateMedicalTechnology(
        private val medicalTechnology: MedicalTechnology,
        private val medicalTechnologyRepository: MedicalTechnologyRepository
    ) : ApplicationService<MedicalTechnology?> {
        override fun execute(): MedicalTechnology? =
            if (this.medicalTechnologyRepository.findBy(medicalTechnology.id, null) == null) {
                this.medicalTechnologyRepository.createMedicalTechnology(medicalTechnology)
            } else null
    }

    /**
     * Application Service that has the objective of getting the information about a specific medical technology
     * identified by a [medicalTechnologyId] that - if specified - are respect a specific [dateTime]
     * using the provided [medicalTechnologyRepository].
     */
    class GetMedicalTechnology(
        private val medicalTechnologyId: MedicalTechnologyID,
        private val medicalTechnologyRepository: MedicalTechnologyRepository,
        private val dateTime: Instant? = null
    ) : ApplicationService<MedicalTechnology?> {
        override fun execute(): MedicalTechnology? =
            this.medicalTechnologyRepository.findBy(medicalTechnologyId, dateTime)
    }

    /**
     * Application Service that has the objective of deleting a medical technology
     * identified by its [medicalTechnologyId] using the provided [medicalTechnologyId].
     */
    class DeleteMedicalTechnology(
        private val medicalTechnologyId: MedicalTechnologyID,
        private val medicalTechnologyRepository: MedicalTechnologyRepository
    ) : ApplicationService<Boolean> {
        override fun execute(): Boolean = this.medicalTechnologyRepository.deleteMedicalTechnology(medicalTechnologyId)
    }

    /**
     * Application Service that has the objective of updating the mapping of a medical technology to the room using
     * the provided [medicalTechnologyRepository].
     * The medical technology is identified by its [medicalTechnologyId] and the room by its [roomID].
     * If the [roomID] is null, then the mapping will be deleted.
     */
    class MapMedicalTechnologyToRoom(
        private val medicalTechnologyId: MedicalTechnologyID,
        private val roomID: RoomID?,
        private val roomRepository: RoomRepository,
        private val medicalTechnologyRepository: MedicalTechnologyRepository
    ) : ApplicationService<Boolean> {
        override fun execute(): Boolean =
            // Check if the medical technology (always) and room exists (if the roomId is not null)
            if (this.medicalTechnologyRepository.findBy(this.medicalTechnologyId, null) != null &&
                (this.roomID == null || this.roomRepository.findBy(this.roomID, null) != null)
            ) {
                this.medicalTechnologyRepository.mapTechnologyTo(medicalTechnologyId, roomID)
            } else false
    }

    /**
     * Application Service that has the objective of updating the [usage] data about a medical technology identified
     * by its [medicalTechnologyId]. The [usage] refers to a specific [dateTime] and it is updated via the provided
     * [medicalTechnologyRepository].
     */
    class UpdateMedicalTechnologyUsage(
        private val medicalTechnologyId: MedicalTechnologyID,
        private val usage: Boolean,
        private val dateTime: Instant,
        private val medicalTechnologyRepository: MedicalTechnologyRepository,
    ) : ApplicationService<Boolean> {
        override fun execute(): Boolean =
            with(this.medicalTechnologyRepository.findBy(medicalTechnologyId, null)) {
                if (this != null && this.roomId != null) {
                    medicalTechnologyRepository.updateMedicalTechnologyUsage(
                        medicalTechnologyId,
                        usage,
                        this.roomId,
                        dateTime
                    )
                } else false
            }
    }
}
