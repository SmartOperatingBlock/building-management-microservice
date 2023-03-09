/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.presenter.event.serialization

import application.presenter.event.model.RoomEventPayloads
import entity.environment.Humidity
import entity.environment.LightUnit
import entity.environment.Luminosity
import entity.environment.Presence
import entity.environment.Temperature
import entity.environment.TemperatureUnit

/** Module that wraps the event serialization. */
object EventSerialization {
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
