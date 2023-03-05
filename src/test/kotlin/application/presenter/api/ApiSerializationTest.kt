/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.api

import application.presenter.api.deserializer.ApiDeserializer.toMedicalTechnology
import application.presenter.api.deserializer.ApiDeserializer.toRoom
import application.presenter.api.model.EnvironmentalDataApiDto
import application.presenter.api.model.MedicalTechnologyApiDto
import application.presenter.api.model.MedicalTechnologyApiDtoType
import application.presenter.api.model.RoomApiDto
import application.presenter.api.model.RoomApiDtoType
import application.presenter.api.model.RoomEntry
import application.presenter.api.model.ValueWithUnit
import application.presenter.api.serializer.ApiSerializer.toMedicalTechnologyApiDto
import application.presenter.api.serializer.ApiSerializer.toRoomApiDto
import application.presenter.api.serializer.ApiSerializer.toRoomEntry
import entity.environment.Humidity
import entity.environment.LightUnit
import entity.environment.Luminosity
import entity.environment.Presence
import entity.environment.Temperature
import entity.environment.TemperatureUnit
import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.medicaltechnology.MedicalTechnologyType
import entity.zone.Room
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import entity.zone.RoomType
import entity.zone.ZoneID
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApiSerializationTest : StringSpec({
    val room = Room(
        RoomID("r1"),
        RoomType.OPERATING_ROOM,
        ZoneID("z1"),
        "name",
        RoomEnvironmentalData(
            Temperature(33.0, TemperatureUnit.CELSIUS),
            Humidity(55.0),
            Luminosity(150.0, LightUnit.LUX),
            Presence(true)
        )
    )
    val roomApiDto = RoomApiDto(
        "r1",
        "name",
        "z1",
        RoomApiDtoType.OPERATING_ROOM,
        EnvironmentalDataApiDto(
            ValueWithUnit(33.0, "CELSIUS"),
            55.0,
            ValueWithUnit(150.0, "LUX"),
            true
        )
    )

    val medicalTechnology = MedicalTechnology(
        id = MedicalTechnologyID("mt-1"),
        name = "name",
        description = "description",
        type = MedicalTechnologyType.ENDOSCOPE,
        isInUse = true,
        roomId = RoomID("r-1")
    )

    val medicalTechnologyApiDto = MedicalTechnologyApiDto(
        id = "mt-1",
        name = "name",
        description = "description",
        type = MedicalTechnologyApiDtoType.ENDOSCOPE,
        inUse = true,
        roomId = "r-1"
    )

    "It should be possible to obtain the corresponding room from the data get from the API" {
        roomApiDto.toRoom() shouldBe room
    }

    "It should be possible to serialize a room in order to send it through the API" {
        room.toRoomApiDto() shouldBe roomApiDto
    }

    "It should be possible to serialize a room in a room entry" {
        room.toRoomEntry() shouldBe RoomEntry(
            id = "r1",
            name = "name",
            zoneId = "z1",
            type = "OPERATING_ROOM"
        )
    }

    "It should be possible to obtain the corresponding medical technology from the data get from the API" {
        medicalTechnologyApiDto.toMedicalTechnology() shouldBe medicalTechnology
    }

    "It should be possible to serialize a medical technology in order to send it through the API" {
        medicalTechnology.toMedicalTechnologyApiDto() shouldBe medicalTechnologyApiDto
    }
})
