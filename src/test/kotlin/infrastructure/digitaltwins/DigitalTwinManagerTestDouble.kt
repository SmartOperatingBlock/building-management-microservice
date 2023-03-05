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
import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.Room
import entity.zone.RoomID

/**
 * Simple Test Double (fake) for [RoomDigitalTwinManager] in order to perform tests.
 */
class DigitalTwinManagerTestDouble : RoomDigitalTwinManager, MedicalTechnologyDigitalTwinManager {
    private val rooms: MutableSet<Room> = mutableSetOf()
    private val medicalTechnologies: MutableSet<MedicalTechnology> = mutableSetOf()

    override fun createRoomDigitalTwin(room: Room): Boolean {
        this.rooms.add(room)
        return true
    }

    override fun deleteRoomDigitalTwin(roomId: RoomID): Boolean = this.rooms.removeIf { it.id == roomId }

    override fun findBy(roomId: RoomID): Room? = this.rooms.find { it.id == roomId }

    override fun createMedicalTechnologyDigitalTwin(medicalTechnology: MedicalTechnology): Boolean {
        this.medicalTechnologies.add(medicalTechnology)
        return true
    }

    override fun deleteMedicalTechnologyDigitalTwin(medicalTechnologyId: MedicalTechnologyID): Boolean =
        this.medicalTechnologies.removeIf { it.id == medicalTechnologyId }

    override fun findBy(medicalTechnologyId: MedicalTechnologyID): MedicalTechnology? =
        this.medicalTechnologies.find { it.id == medicalTechnologyId }

    override fun mapTo(medicalTechnologyId: MedicalTechnologyID, roomId: RoomID?): Boolean =
        with(this.findBy(medicalTechnologyId)) {
            if (this != null) {
                this@DigitalTwinManagerTestDouble.deleteMedicalTechnologyDigitalTwin(medicalTechnologyId)
                this@DigitalTwinManagerTestDouble.createMedicalTechnologyDigitalTwin(this.copy(roomId = roomId))
                true
            } else {
                false
            }
        }
}
