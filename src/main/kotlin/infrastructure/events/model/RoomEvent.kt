/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.events.model

import entity.environment.Humidity
import entity.environment.LightUnit
import entity.environment.Luminosity
import entity.environment.Presence
import entity.environment.Temperature
import entity.environment.TemperatureUnit
import kotlinx.serialization.Serializable

/** Interface that identify a data payload that is accepted inside a [RoomEvent]. */
interface RoomEventPayload

/**
 * Room environment conditions update event.
 * In addition to a normal [Event] it has the [roomId] to which the event refers.
 */
@Serializable
data class RoomEvent<E : RoomEventPayload>(
    override val key: String,
    val roomId: String,
    override val data: E,
    override val dateTime: String
) : Event<E>

/** Module that wraps all the [RoomEvent] data payloads. */
object RoomEventPayloads {
    /**
     * [RoomEvent] payload with the humidity of the room.
     * @param humidityPercentage the percentage of humidity.
     */
    @Serializable
    data class HumidityPayload(val humidityPercentage: Int) : RoomEventPayload

    /**
     * [RoomEvent] payload with the temperature of the room.
     * @param temperatureValue the temperature of the room.
     * @param temperatureUnit the temperature unit.
     */
    @Serializable
    data class TemperaturePayload(
        val temperatureValue: Double,
        val temperatureUnit: TemperaturePayloadUnit
    ) : RoomEventPayload

    /**
     * [RoomEvent] payload with the luminosity of the room.
     * @param luminosityValue the luminosity value of the room.
     * @param luminosityUnit the luminosity unit.
     */
    @Serializable
    data class LuminosityPayload(
        val luminosityValue: Double,
        val luminosityUnit: LuminosityPayloadUnit
    ) : RoomEventPayload

    /**
     * [RoomEvent] payload with the presence of a person inside the room.
     * @param presenceDetected true if is a person detection event, false otherwise.
     */
    @Serializable
    data class PresencePayload(val presenceDetected: Boolean) : RoomEventPayload

    /** The [TemperaturePayload] unit. **/
    @Serializable
    enum class TemperaturePayloadUnit {
        CELSIUS
    }

    /** The [LuminosityPayload] unit. **/
    @Serializable
    enum class LuminosityPayloadUnit {
        LUX
    }

    /** Convert a Temperature Payload to [Temperature]. */
    fun TemperaturePayload.toTemperature() =
        Temperature(this.temperatureValue, this.temperatureUnit.toTemperatureUnit())

    /** Convert a Luminosity Payload to [Luminosity]. */
    fun LuminosityPayload.toLuminosity() = Luminosity(this.luminosityValue, this.luminosityUnit.toLightUnit())

    /** Convert a Humidity Payload to [Humidity]. */
    fun HumidityPayload.toHumidity() = Humidity(this.humidityPercentage.toDouble())

    /** Convert a Presence Payload to [Presence]. */
    fun PresencePayload.toPresence() = Presence(this.presenceDetected)

    private fun TemperaturePayloadUnit.toTemperatureUnit() = when (this) {
        TemperaturePayloadUnit.CELSIUS -> TemperatureUnit.CELSIUS
    }

    private fun LuminosityPayloadUnit.toLightUnit() = when (this) {
        LuminosityPayloadUnit.LUX -> LightUnit.LUX
    }
}

/** Module that wraps the possible keys for a [RoomEvent]. */
object RoomEventKey {
    /** Temperature update event. */
    const val TEMPERATURE_EVENT = "TEMPERATURE_EVENT"
    /** Humidity update event. */
    const val HUMIDITY_EVENT = "HUMIDITY_EVENT"
    /** Luminosity update event. */
    const val LUMINOSITY_EVENT = "LUMINOSITY_EVENT"
    /** Presence update event. */
    const val PRESENCE_EVENT = "PRESENCE_EVENT"
}
