/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.database.datamodel

import entity.zone.RoomID
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

/**
 * This class models a time series data of a room
 * This data is respect to a specific [dateTime].
 * Its [metadata] will be useful in order to correctly interpret the carried [value].
 */
@Serializable
data class TimeSeriesRoomEnvironmentalData(
    @Contextual val dateTime: Instant,
    val metadata: TimeSeriesRoomMetadata,
    val value: Double
)

/**
 * This class represents the metadata about data in time series.
 * It contains the [roomId] and the [type] of data.
 * The data could be expressed with an [unit] of measurement.
 */
@Serializable
data class TimeSeriesRoomMetadata(val roomId: RoomID, val type: TimeSeriesDataType, val unit: String? = null)

/**
 * The type of data that is inside the time series.
 */
@Serializable
enum class TimeSeriesDataType {
    TEMPERATURE,
    HUMIDITY,
    LUMINOSITY,
    PRESENCE
}
