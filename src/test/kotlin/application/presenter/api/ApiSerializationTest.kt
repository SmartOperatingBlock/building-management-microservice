/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.api

import application.presenter.api.deserializer.ApiDeserializer.toRoom
import application.presenter.api.model.EnvironmentalDataApiDto
import application.presenter.api.model.RoomApiDto
import application.presenter.api.model.RoomApiDtoType
import application.presenter.api.model.ValueWithUnit
import application.presenter.api.serializer.ApiSerializer.toRoomApiDto
import entity.environment.Humidity
import entity.environment.LightUnit
import entity.environment.Luminosity
import entity.environment.Presence
import entity.environment.Temperature
import entity.environment.TemperatureUnit
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

    "It should be possible to obtain the corresponding room from the data get from the API" {
        roomApiDto.toRoom() shouldBe room
    }

    "It should be possibile to serialize a room in order to send it through the API" {
        room.toRoomApiDto() shouldBe roomApiDto
    }
})
