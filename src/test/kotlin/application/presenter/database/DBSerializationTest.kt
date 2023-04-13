/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.database

import application.presenter.database.model.TimeSeriesDataType
import application.presenter.database.model.TimeSeriesRoomEnvironmentalData
import application.presenter.database.model.TimeSeriesRoomMetadata
import application.presenter.database.serialization.toRoomEnvironmentalData
import application.presenter.database.serialization.toTimeSeries
import entity.environment.Humidity
import entity.environment.LightUnit
import entity.environment.Luminosity
import entity.environment.Presence
import entity.environment.Temperature
import entity.environment.TemperatureUnit
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.Instant

class DBSerializationTest : StringSpec({
    val roomId = RoomID("test")
    val dateTime = Instant.now()
    val roomEnvironmentalData = RoomEnvironmentalData(
        Temperature(33.0, TemperatureUnit.CELSIUS),
        Humidity(55.0),
        Luminosity(150.0, LightUnit.LUX),
        Presence(true),
    )
    val timeSeries = mapOf(
        TimeSeriesDataType.TEMPERATURE to TimeSeriesRoomEnvironmentalData(
            dateTime,
            TimeSeriesRoomMetadata(roomId, TimeSeriesDataType.TEMPERATURE, TemperatureUnit.CELSIUS.toString()),
            33.0,
        ),
        TimeSeriesDataType.HUMIDITY to TimeSeriesRoomEnvironmentalData(
            dateTime,
            TimeSeriesRoomMetadata(roomId, TimeSeriesDataType.HUMIDITY),
            55.0,
        ),
        TimeSeriesDataType.LUMINOSITY to TimeSeriesRoomEnvironmentalData(
            dateTime,
            TimeSeriesRoomMetadata(roomId, TimeSeriesDataType.LUMINOSITY, LightUnit.LUX.toString()),
            150.0,
        ),
        TimeSeriesDataType.PRESENCE to TimeSeriesRoomEnvironmentalData(
            dateTime,
            TimeSeriesRoomMetadata(roomId, TimeSeriesDataType.PRESENCE),
            1.0,
        ),
    )

    "It should be possible to convert a map of time series data to the room environmental data" {
        timeSeries.toRoomEnvironmentalData() shouldBe roomEnvironmentalData
    }

    "It should be possible to obtain the time series from the environmental data of a room" {
        roomEnvironmentalData.toTimeSeries(dateTime, roomId) shouldBe timeSeries
    }
})
