/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.api.deserializer

import application.presenter.api.model.MedicalTechnologyApiDto
import application.presenter.api.model.MedicalTechnologyApiDtoType
import application.presenter.api.model.RoomApiDtoType
import application.presenter.api.model.RoomEntry
import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.medicaltechnology.MedicalTechnologyType
import entity.zone.Room
import entity.zone.RoomID
import entity.zone.RoomType
import entity.zone.ZoneID

/**
 * Deserializer for data that comes from API.
 */
object ApiDeserializer {
    /**
     * Extension method to convert Room API DTO to [entity.zone.Room] class.
     */
    fun RoomEntry.toRoom() = Room(
        id = RoomID(this.id),
        type = this.type.toRoomType(),
        zoneId = ZoneID(this.zoneId),
        name = this.name
    )

    private fun RoomApiDtoType.toRoomType() = when (this) {
        RoomApiDtoType.OPERATING_ROOM -> RoomType.OPERATING_ROOM
        RoomApiDtoType.PRE_OPERATING_ROOM -> RoomType.PRE_OPERATING_ROOM
    }

    /**
     * Extension method to convert Medical Technology API DTO to [entity.medicaltechnology.MedicalTechnology] class.
     */
    fun MedicalTechnologyApiDto.toMedicalTechnology() = MedicalTechnology(
        id = MedicalTechnologyID(this.id),
        name = this.name,
        description = this.description,
        type = this.type.toMedicalTechnologyType(),
        isInUse = this.inUse,
        roomId = this.roomId?.let { RoomID(it) }
    )

    private fun MedicalTechnologyApiDtoType.toMedicalTechnologyType() = when (this) {
        MedicalTechnologyApiDtoType.ENDOSCOPE -> MedicalTechnologyType.ENDOSCOPE
        MedicalTechnologyApiDtoType.XRAY -> MedicalTechnologyType.XRAY
    }
}
