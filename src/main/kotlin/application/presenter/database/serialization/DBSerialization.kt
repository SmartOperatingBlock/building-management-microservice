/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.database.serialization

import application.presenter.database.model.TimeSeriesDataType
import application.presenter.database.model.TimeSeriesRoomEnvironmentalData
import application.presenter.database.model.TimeSeriesRoomMetadata
import entity.environment.Humidity
import entity.environment.LightUnit
import entity.environment.Luminosity
import entity.environment.Presence
import entity.environment.Temperature
import entity.environment.TemperatureUnit
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import java.time.Instant

/**
 * Extension method that allows to convert a map of [TimeSeriesDataType] -> [TimeSeriesRoomEnvironmentalData]
 * to domain [RoomEnvironmentalData].
 * @return the resulting [RoomEnvironmentalData].
 */
fun Map<TimeSeriesDataType, TimeSeriesRoomEnvironmentalData?>.toRoomEnvironmentalData() =
    RoomEnvironmentalData(
        temperature = this[TimeSeriesDataType.TEMPERATURE]?.let {
            Temperature(
                it.value,
                it.metadata.unit?.let { unit -> TemperatureUnit.valueOf(unit) } ?: TemperatureUnit.CELSIUS
            )
        },
        humidity = this[TimeSeriesDataType.HUMIDITY]?.let { Humidity(it.value) },
        luminosity = this[TimeSeriesDataType.LUMINOSITY]?.let {
            Luminosity(
                it.value,
                it.metadata.unit?.let { unit -> LightUnit.valueOf(unit) } ?: LightUnit.LUX
            )
        },
        presence = this[TimeSeriesDataType.PRESENCE]?.let { Presence(it.value > 0) }
    )

/**
 * Extension method that allows to convert [RoomEnvironmentalData] in a map of [TimeSeriesRoomEnvironmentalData].
 */
fun RoomEnvironmentalData.toTimeSeries(dateTime: Instant, roomId: RoomID) = mapOf(
    TimeSeriesDataType.TEMPERATURE to this.temperature?.let {
        TimeSeriesRoomEnvironmentalData(
            dateTime,
            TimeSeriesRoomMetadata(
                roomId,
                TimeSeriesDataType.TEMPERATURE,
                it.unit.toString()
            ),
            it.value
        )
    },
    TimeSeriesDataType.HUMIDITY to this.humidity?.let {
        TimeSeriesRoomEnvironmentalData(
            dateTime,
            TimeSeriesRoomMetadata(roomId, TimeSeriesDataType.HUMIDITY),
            it.percentage
        )
    },
    TimeSeriesDataType.LUMINOSITY to this.luminosity?.let {
        TimeSeriesRoomEnvironmentalData(
            dateTime,
            TimeSeriesRoomMetadata(
                roomId,
                TimeSeriesDataType.LUMINOSITY,
                it.unit.toString()
            ),
            it.value
        )
    },
    TimeSeriesDataType.PRESENCE to this.presence?.let {
        TimeSeriesRoomEnvironmentalData(
            dateTime,
            TimeSeriesRoomMetadata(roomId, TimeSeriesDataType.PRESENCE),
            it.presenceDetected.let { p -> if (p) 1.0 else 0.0 }
        )
    }
).filter { it.value != null }
