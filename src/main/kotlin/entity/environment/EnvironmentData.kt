/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package entity.environment

import kotlinx.serialization.Serializable

/**
 * Temperature concept.
 * It is described by the current temperature [value] expressed in a [unit].
 */
@Serializable
data class Temperature(val value: Double, val unit: TemperatureUnit = TemperatureUnit.CELSIUS)

/**
 * This enum describe the possible [Temperature] unit of measurement.
 */
@Serializable
enum class TemperatureUnit {
    /**
     * Celsius unit.
     */
    CELSIUS,
}

/**
 * Humidity concept.
 * It is described by the current [percentage] of humidity. So it describes the Relative Humidity.
 */
@Serializable
data class Humidity(val percentage: Double)

/**
 * Luminosity concept.
 * It is described by the current luminosity [value] expressed in a [unit].
 */
@Serializable
data class Luminosity(val value: Double, val unit: LightUnit = LightUnit.LUX) {
    init {
        // Constructor validation
        require(this.value >= 0)
    }
}

/**
 * This enum describe the possible [Luminosity] unit of measurement.
 */
@Serializable
enum class LightUnit {
    /**
     * Lux unit.
     */
    LUX,
}

/**
 * Describe the presence inside a Room of the Operating Block.
 * @param[presenceDetected] true if someone is in the room, false otherwise.
 */
@Serializable
data class Presence(val presenceDetected: Boolean)
