/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.digitaltwins.adtpresentation

import com.azure.digitaltwins.core.BasicDigitalTwin
import com.azure.digitaltwins.core.BasicDigitalTwinMetadata
import entity.environment.Humidity
import entity.environment.LightUnit
import entity.environment.Luminosity
import entity.environment.Presence
import entity.environment.Temperature
import entity.environment.TemperatureUnit
import entity.zone.Room
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import entity.zone.RoomType
import entity.zone.ZoneID

/**
 * Module to wrap all the presentation-related stuff for Azure Digital Twins with [entity.zone.Room].
 */
object RoomAdtPresentation {
    /** Operating room Azure Digital Twins model. */
    private const val OPERATING_ROOM_MODEL = "dtmi:io:github:smartoperatingblock:OperatingRoom;1"

    /** Pre-operating room Azure Digital Twins model. */
    private const val PRE_OPERATING_ROOM_MODEL = "dtmi:io:github:smartoperatingblock:PrePostOperatingRoom;1"
    private const val NAME_PROPERTY = "name"
    private const val ZONE_ID_PROPERTY = "zone_id"
    private const val TEMPERATURE_PROPERTY = "temperature"
    private const val HUMIDITY_PROPERTY = "humidity"
    private const val PRESENCE_INSIDE_PROPERTY = "presence_inside"
    private const val LUMINOSITY_PROPERTY = "luminosity"

    /**
     * Convert a [Room] to a Digital Twin.
     * Specifically this extension method will convert it into the Azure Digital Twins SDK [BasicDigitalTwin].
     * @return the corresponding [BasicDigitalTwin].
     */
    fun Room.toDigitalTwin(): BasicDigitalTwin =
        BasicDigitalTwin(this.id.value)
            .setMetadata(
                BasicDigitalTwinMetadata().setModelId(
                    when (this.type) {
                        RoomType.OPERATING_ROOM -> OPERATING_ROOM_MODEL
                        RoomType.PRE_OPERATING_ROOM -> PRE_OPERATING_ROOM_MODEL
                    },
                ),
            )
            .addToContents(NAME_PROPERTY, this.name.orEmpty())
            .addToContents(ZONE_ID_PROPERTY, this.zoneId.value)

    /**
     * Obtain a [Room] instance from its Azure Digital Twins' [BasicDigitalTwin].
     * @return the corresponding room.
     */
    fun BasicDigitalTwin.toRoom(): Room =
        Room(
            id = RoomID(this.id),
            zoneId = ZoneID(this.contents[ZONE_ID_PROPERTY].propertyAs(defaultValue = "")),
            name = this.contents[NAME_PROPERTY].propertyAs(defaultValue = ""),
            type = if (this.metadata.modelId == OPERATING_ROOM_MODEL) {
                RoomType.OPERATING_ROOM
            } else {
                RoomType.PRE_OPERATING_ROOM
            },
            environmentalData = RoomEnvironmentalData(
                temperature = this.contents[TEMPERATURE_PROPERTY]?.let {
                    Temperature((it as Number).toDouble(), TemperatureUnit.CELSIUS)
                },
                humidity = this.contents[HUMIDITY_PROPERTY]?.let { Humidity((it as Number).toDouble()) },
                luminosity = this.contents[LUMINOSITY_PROPERTY]?.let {
                    Luminosity((it as Number).toDouble(), LightUnit.LUX)
                },
                presence = this.contents[PRESENCE_INSIDE_PROPERTY]?.let { Presence(it as Boolean) },
            ),
        )
}
