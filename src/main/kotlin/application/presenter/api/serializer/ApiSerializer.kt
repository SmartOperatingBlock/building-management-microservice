/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.api.serializer

import application.presenter.api.model.EnvironmentalDataApiDto
import application.presenter.api.model.MedicalTechnologyApiDto
import application.presenter.api.model.MedicalTechnologyApiDtoType
import application.presenter.api.model.RoomApiDto
import application.presenter.api.model.RoomApiDtoType
import application.presenter.api.model.RoomEntry
import application.presenter.api.model.ValueWithUnit
import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyType
import entity.zone.Room
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomType

/**
 * Serializer for data to return in API.
 */
object ApiSerializer {
    /**
     * Extension method to obtain the room entry information.
     * @return the room entry.
     */
    fun Room.toRoomEntry(): RoomEntry = RoomEntry(
        id = this.id.value,
        name = this.name.orEmpty(),
        zoneId = this.zoneId.value,
        type = this.type.toRoomApiDtoType(),
    )

    /**
     * Extension method to convert [Room] to [RoomApiDto] class.
     */
    fun Room.toRoomApiDto(): RoomApiDto = RoomApiDto(
        id = this.id.value,
        name = this.name.orEmpty(),
        zoneId = this.zoneId.value,
        type = this.type.toRoomApiDtoType(),
        environmentalData = this.environmentalData.toEnvironmentDataApiDto(),
    )

    /**
     * Extension method to convert [RoomEnvironmentalData] to [EnvironmentalDataApiDto] class.
     */
    fun RoomEnvironmentalData.toEnvironmentDataApiDto() = EnvironmentalDataApiDto(
        temperature = this.temperature?.let { ValueWithUnit(it.value, it.unit.toString()) },
        humidity = this.humidity?.percentage,
        luminosity = this.luminosity?.let { ValueWithUnit(it.value, it.unit.toString()) },
        presence = this.presence?.presenceDetected,
    )

    private fun RoomType.toRoomApiDtoType() = when (this) {
        RoomType.OPERATING_ROOM -> RoomApiDtoType.OPERATING_ROOM
        RoomType.PRE_OPERATING_ROOM -> RoomApiDtoType.PRE_OPERATING_ROOM
    }

    /**
     * Extension method to convert [MedicalTechnology] API DTO
     * to [application.presenter.api.model.MedicalTechnologyApiDto] class.
     */
    fun MedicalTechnology.toMedicalTechnologyApiDto() = MedicalTechnologyApiDto(
        id = this.id.value,
        name = this.name,
        description = this.description,
        type = this.type.toMedicalTechnologyApiDtoType(),
        inUse = this.isInUse,
        roomId = this.roomId?.value,
    )

    private fun MedicalTechnologyType.toMedicalTechnologyApiDtoType() = when (this) {
        MedicalTechnologyType.ENDOSCOPE -> MedicalTechnologyApiDtoType.ENDOSCOPE
        MedicalTechnologyType.XRAY -> MedicalTechnologyApiDtoType.XRAY
    }
}
