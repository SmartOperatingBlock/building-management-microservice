/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.event.serialization

import application.presenter.event.model.Event
import application.presenter.event.model.MedicalTechnologyEvent
import application.presenter.event.model.MedicalTechnologyEventKey
import application.presenter.event.model.RoomEvent
import application.presenter.event.model.RoomEventKey
import application.presenter.event.model.RoomEventPayloads
import entity.environment.Humidity
import entity.environment.LightUnit
import entity.environment.Luminosity
import entity.environment.Presence
import entity.environment.Temperature
import entity.environment.TemperatureUnit
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/** Module that wraps the event serialization. */
object EventSerialization {
    /**
     * Convert an event body to an [Event] object giving its [eventKey].
     * @throws IllegalArgumentException if the event cannot be deserialized.
     */
    fun String.toEvent(eventKey: String): Event<*> = when (eventKey) {
        RoomEventKey.TEMPERATURE_EVENT -> Json.decodeFromString<RoomEvent<RoomEventPayloads.TemperaturePayload>>(this)
        RoomEventKey.HUMIDITY_EVENT -> Json.decodeFromString<RoomEvent<RoomEventPayloads.HumidityPayload>>(this)
        RoomEventKey.LUMINOSITY_EVENT -> Json.decodeFromString<RoomEvent<RoomEventPayloads.LuminosityPayload>>(this)
        RoomEventKey.PRESENCE_EVENT -> Json.decodeFromString<RoomEvent<RoomEventPayloads.PresencePayload>>(this)
        MedicalTechnologyEventKey.USAGE_EVENT -> Json.decodeFromString<MedicalTechnologyEvent>(this)
        else -> throw IllegalArgumentException("Event not supported")
    }

    /** Convert a Temperature Payload to [Temperature]. */
    fun RoomEventPayloads.TemperaturePayload.toTemperature() =
        Temperature(this.temperatureValue, this.temperatureUnit.toTemperatureUnit())

    /** Convert a Luminosity Payload to [Luminosity]. */
    fun RoomEventPayloads.LuminosityPayload.toLuminosity() =
        Luminosity(this.luminosityValue, this.luminosityUnit.toLightUnit())

    /** Convert a Humidity Payload to [Humidity]. */
    fun RoomEventPayloads.HumidityPayload.toHumidity() = Humidity(this.humidityPercentage.toDouble())

    /** Convert a Presence Payload to [Presence]. */
    fun RoomEventPayloads.PresencePayload.toPresence() = Presence(this.presenceDetected)

    private fun RoomEventPayloads.TemperaturePayloadUnit.toTemperatureUnit() = when (this) {
        RoomEventPayloads.TemperaturePayloadUnit.CELSIUS -> TemperatureUnit.CELSIUS
    }

    private fun RoomEventPayloads.LuminosityPayloadUnit.toLightUnit() = when (this) {
        RoomEventPayloads.LuminosityPayloadUnit.LUX -> LightUnit.LUX
    }
}
