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
import application.handler.EventHandler
import application.handler.EventHandlers
import application.presenter.event.serialization.EventSerialization.toEvent
import com.fasterxml.jackson.databind.ObjectMapper
import infrastructure.provider.ManagerProvider
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.time.format.DateTimeParseException

/**
 * This class manage the Kafka client needed to consume events.
 * @param[provider] the provider of managers.
 */
class KafkaClient(private val provider: ManagerProvider) {
    private val eventHandlers: List<EventHandler>

    init {
        checkNotNull(System.getenv(bootstrapServerUrlVariable)) { "kafka bootstrap server url required" }
        checkNotNull(System.getenv(schemaRegistryUrlVariable)) { "kafka schema registry url required" }
        val roomController = RoomController(provider.roomDigitalTwinManager, provider.roomDatabaseManager)
        val medicalTechnologyController = MedicalTechnologyController(
            provider.medicalTechnologyDigitalTwinManager,
            provider.medicalTechnologyDatabaseManager,
        )
        eventHandlers = listOf(
            EventHandlers.TemperatureEventHandler(roomController),
            EventHandlers.HumidityEventHandler(roomController),
            EventHandlers.LuminosityEventHandler(roomController),
            EventHandlers.PresenceEventHandler(roomController),
            EventHandlers.MedicalTechnologyEventHandler(medicalTechnologyController),
        )
    }

    private val kafkaConsumer = KafkaConsumer<String, String>(
        loadConsumerProperties(
            System.getenv(bootstrapServerUrlVariable),
            System.getenv(schemaRegistryUrlVariable),
        ),
    )

    /**
     * Start consuming event on Kafka.
     */
    fun start() {
        this.kafkaConsumer.subscribe(listOf(roomEventsTopic, medicalTechnologyEventTopic)).run {
            while (true) {
                kafkaConsumer.poll(Duration.ofMillis(pollingTime)).forEach { event ->
                    try {
                        consumeEvent(event)
                    } catch (e: IllegalArgumentException) {
                        println("INFO: Event discarded! - $e")
                    } catch (e: DateTimeParseException) {
                        println("ERROR: Invalid Date in event. Event discarded! - $e")
                    }
                }
            }
        }
    }

    private fun consumeEvent(event: ConsumerRecord<String, String>) {
        val deserializedEvent = ObjectMapper().writeValueAsString(event.value()).toEvent(event.key())
        this@KafkaClient.eventHandlers
            .filter { it.canHandle(deserializedEvent) }
            .forEach { it.consume(deserializedEvent) }
    }

    companion object {
        private const val bootstrapServerUrlVariable = "BOOTSTRAP_SERVER_URL"
        private const val schemaRegistryUrlVariable = "SCHEMA_REGISTRY_URL"
        private const val roomEventsTopic = "room-events"
        private const val medicalTechnologyEventTopic = "process-events"
        private const val pollingTime = 100L
    }
}
