/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.events

import application.controller.MedicalTechnologyController
import application.controller.RoomController
import application.service.MedicalTechnologyService
import application.service.RoomService
import com.fasterxml.jackson.databind.ObjectMapper
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import infrastructure.events.model.MedicalTechnologyEvent
import infrastructure.events.model.MedicalTechnologyEventKey
import infrastructure.events.model.RoomEvent
import infrastructure.events.model.RoomEventKey
import infrastructure.events.model.RoomEventPayloads
import infrastructure.events.model.RoomEventPayloads.toHumidity
import infrastructure.events.model.RoomEventPayloads.toLuminosity
import infrastructure.events.model.RoomEventPayloads.toPresence
import infrastructure.events.model.RoomEventPayloads.toTemperature
import infrastructure.provider.ManagerProvider
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.time.Instant
import java.time.format.DateTimeParseException

/**
 * This class manage the Kafka client needed to consume events.
 * @param[provider] the provider of managers.
 */
class KafkaClient(private val provider: ManagerProvider) {
    init {
        checkNotNull(System.getenv(bootstrapServerUrlVariable)) { "kafka bootstrap server url required" }
        checkNotNull(System.getenv(schemaRegistryUrlVariable)) { "kafka schema registry url required" }
    }

    private val kafkaConsumer = KafkaConsumer<String, String>(
        loadConsumerProperties(
            System.getenv(bootstrapServerUrlVariable),
            System.getenv(schemaRegistryUrlVariable)
        )
    )

    /**
     * Start consuming event on Kafka.
     */
    fun start() {
        this.kafkaConsumer.subscribe(listOf(topic)).run {
            while (true) {
                kafkaConsumer.poll(Duration.ofMillis(pollingTime)).forEach { event ->
                    try {
                        consumeEvent(event.key(), ObjectMapper().writeValueAsString(event.value()))
                    } catch (e: IllegalArgumentException) {
                        println("ERROR: Invalid Event Schema. Event discarded! - $e")
                    } catch (e: DateTimeParseException) {
                        println("ERROR: Invalid Date in event. Event discarded! - $e")
                    }
                }
            }
        }
    }

    private fun consumeEvent(eventKey: String, event: String) {
        fun updateRoomEnvironmentData(event: RoomEvent<*>, environmentalData: RoomEnvironmentalData) =
            RoomService.UpdateRoomEnvironmentData(
                RoomID(event.roomId),
                environmentalData,
                Instant.parse(event.dateTime),
                RoomController(provider.roomDigitalTwinManager, provider.roomDatabaseManager)
            ).execute()

        when (eventKey) {
            RoomEventKey.TEMPERATURE_EVENT -> {
                val deserializedEvent = Json.decodeFromString<RoomEvent<RoomEventPayloads.TemperaturePayload>>(event)
                updateRoomEnvironmentData(
                    deserializedEvent,
                    RoomEnvironmentalData(temperature = deserializedEvent.data.toTemperature())
                )
            }
            RoomEventKey.HUMIDITY_EVENT -> {
                val deserializedEvent = Json.decodeFromString<RoomEvent<RoomEventPayloads.HumidityPayload>>(event)
                updateRoomEnvironmentData(
                    deserializedEvent,
                    RoomEnvironmentalData(humidity = deserializedEvent.data.toHumidity())
                )
            }
            RoomEventKey.LUMINOSITY_EVENT -> {
                val deserializedEvent = Json.decodeFromString<RoomEvent<RoomEventPayloads.LuminosityPayload>>(event)
                updateRoomEnvironmentData(
                    deserializedEvent,
                    RoomEnvironmentalData(luminosity = deserializedEvent.data.toLuminosity())
                )
            }
            RoomEventKey.PRESENCE_EVENT -> {
                val deserializedEvent = Json.decodeFromString<RoomEvent<RoomEventPayloads.PresencePayload>>(event)
                updateRoomEnvironmentData(
                    deserializedEvent,
                    RoomEnvironmentalData(presence = deserializedEvent.data.toPresence())
                )
            }
            MedicalTechnologyEventKey.USAGE_EVENT -> {
                val deserializedEvent = Json.decodeFromString<MedicalTechnologyEvent>(event)
                MedicalTechnologyService.UpdateMedicalTechnologyUsage(
                    MedicalTechnologyID(deserializedEvent.data.medicalTechnologyID),
                    deserializedEvent.data.isInUse,
                    Instant.parse(deserializedEvent.dateTime),
                    MedicalTechnologyController(
                        provider.medicalTechnologyDigitalTwinManager,
                        provider.medicalTechnologyDatabaseManager
                    )
                )
            }
            else -> throw IllegalArgumentException("Event not supported")
        }
    }

    companion object {
        private const val bootstrapServerUrlVariable = "BOOTSTRAP_SERVER_URL"
        private const val schemaRegistryUrlVariable = "SCHEMA_REGISTRY_URL"
        private const val topic = "room-events"
        private const val pollingTime = 100L
    }
}
