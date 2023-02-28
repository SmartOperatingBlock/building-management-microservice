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
import entity.environment.Humidity
import entity.environment.LightUnit
import entity.environment.Luminosity
import entity.environment.Presence
import entity.environment.Temperature
import entity.environment.TemperatureUnit
import entity.zone.RoomEnvironmentalData

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
