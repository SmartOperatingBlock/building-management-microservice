/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.api.model

import kotlinx.serialization.Serializable

/**
 * Presenter class to be able to deserialize data that comes from API.
 * It deserializes [id], [name], [zoneId], the [type] of the room and [environmentalData].
 */
@Serializable
data class RoomApiDto(
    val id: String,
    val name: String,
    val zoneId: String,
    val type: RoomApiDtoType,
    val environmentalData: EnvironmentalDataApiDto = EnvironmentalDataApiDto()
)

/**
 * Presenter enum class to deserialize room type that comes from API.
 */
@Serializable
enum class RoomApiDtoType {
    /** Operating room type. */
    OPERATING_ROOM,
    /** Pre-operating room type. */
    PRE_OPERATING_ROOM
}

/**
 * Describes a [value] with a [unit] of measurement.
 */
@Serializable
data class ValueWithUnit<T>(val value: T, val unit: String)

/**
 * Wraps all the environmental data associated to a Room.
 * It corresponds to the model used with the API.
 * So it describe:
 * - the [temperature] inside the room
 * - the [humidity] inside the room
 * - the [luminosity] inside the room
 * - the [presence] of someone in the room
 * All the data may be not present.
 */
@Serializable
data class EnvironmentalDataApiDto(
    val temperature: ValueWithUnit<Double>? = null,
    val humidity: Double? = null,
    val luminosity: ValueWithUnit<Double>? = null,
    val presence: Boolean? = null
)
