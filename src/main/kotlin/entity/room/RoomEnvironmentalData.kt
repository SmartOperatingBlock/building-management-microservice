/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package entity.room

import entity.environment.Humidity
import entity.environment.Luminosity
import entity.environment.Presence
import entity.environment.Temperature

/**
 * Wraps all the environmental data associated to a Room.
 * So it describe:
 * - the [temperature] inside the room
 * - the [humidity] inside the room
 * - the [luminosity] inside the room
 * - the [presence] of someone in the room
 */
data class RoomEnvironmentalData(
    val temperature: Temperature,
    val humidity: Humidity,
    val luminosity: Luminosity,
    val presence: Presence,
)
