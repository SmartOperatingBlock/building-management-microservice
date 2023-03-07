/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package infrastructure.events

import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration

/**
 * This class manage the Kafka client needed to consume events.
 */
class KafkaClient {
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
                    val roomEvent = event.toString()
                    println("New event: $roomEvent")
                }
            }
        }
    }

    companion object {
        private const val bootstrapServerUrlVariable = "BOOTSTRAP_SERVER_URL"
        private const val schemaRegistryUrlVariable = "SCHEMA_REGISTRY_URL"
        private const val topic = "room-events"
        private const val pollingTime = 100L
    }
}
