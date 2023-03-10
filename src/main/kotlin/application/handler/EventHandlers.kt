/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.handler

import application.presenter.event.model.Event
import application.presenter.event.model.MedicalTechnologyEvent
import application.presenter.event.model.RoomEvent
import application.presenter.event.model.RoomEventPayloads
import application.presenter.event.serialization.EventSerialization.toHumidity
import application.presenter.event.serialization.EventSerialization.toLuminosity
import application.presenter.event.serialization.EventSerialization.toPresence
import application.presenter.event.serialization.EventSerialization.toTemperature
import application.service.MedicalTechnologyService
import application.service.RoomService
import entity.medicaltechnology.MedicalTechnologyID
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import usecase.repository.MedicalTechnologyRepository
import usecase.repository.RoomRepository
import java.time.Instant

/**
 * Module that wraps the event handlers.
 */
object EventHandlers {
    /** Event handler for room temperature update event. */
    class TemperatureEventHandler(private val roomRepository: RoomRepository) : EventHandler {
        override fun canHandle(event: Event<*>): Boolean = event.cast<RoomEvent<*>> {
            this.data.cast<RoomEventPayloads.TemperaturePayload>()
        }

        override fun consume(event: Event<*>) {
            event.cast<RoomEvent<RoomEventPayloads.TemperaturePayload>>() {
                updateRoomEnvironmentData(
                    this,
                    RoomEnvironmentalData(temperature = this.data.toTemperature()),
                    this@TemperatureEventHandler.roomRepository
                )
            }
        }
    }

    /** Event handler for room humidity update event. */
    class HumidityEventHandler(private val roomRepository: RoomRepository) : EventHandler {
        override fun canHandle(event: Event<*>): Boolean = event.cast<RoomEvent<*>> {
            this.data.cast<RoomEventPayloads.HumidityPayload>()
        }

        override fun consume(event: Event<*>) {
            event.cast<RoomEvent<RoomEventPayloads.HumidityPayload>>() {
                updateRoomEnvironmentData(
                    this,
                    RoomEnvironmentalData(humidity = this.data.toHumidity()),
                    this@HumidityEventHandler.roomRepository
                )
            }
        }
    }

    /** Event handler for room luminosity update event. */
    class LuminosityEventHandler(private val roomRepository: RoomRepository) : EventHandler {
        override fun canHandle(event: Event<*>): Boolean = event.cast<RoomEvent<*>> {
            this.data.cast<RoomEventPayloads.LuminosityPayload>()
        }

        override fun consume(event: Event<*>) {
            event.cast<RoomEvent<RoomEventPayloads.LuminosityPayload>>() {
                updateRoomEnvironmentData(
                    this,
                    RoomEnvironmentalData(luminosity = this.data.toLuminosity()),
                    this@LuminosityEventHandler.roomRepository
                )
            }
        }
    }

    /** Event handler for room presence update event. */
    class PresenceEventHandler(private val roomRepository: RoomRepository) : EventHandler {
        override fun canHandle(event: Event<*>): Boolean = event.cast<RoomEvent<*>> {
            this.data.cast<RoomEventPayloads.PresencePayload>()
        }

        override fun consume(event: Event<*>) {
            event.cast<RoomEvent<RoomEventPayloads.PresencePayload>>() {
                updateRoomEnvironmentData(
                    this,
                    RoomEnvironmentalData(presence = this.data.toPresence()),
                    this@PresenceEventHandler.roomRepository
                )
            }
        }
    }

    /** Event handler for medical technology usage update event. */
    class MedicalTechnologyEventHandler(
        private val medicalTechnologyRepository: MedicalTechnologyRepository
    ) : EventHandler {
        override fun canHandle(event: Event<*>): Boolean = event is MedicalTechnologyEvent

        override fun consume(event: Event<*>) {
            event.cast<MedicalTechnologyEvent>() {
                MedicalTechnologyService.UpdateMedicalTechnologyUsage(
                    MedicalTechnologyID(this.data.medicalTechnologyID),
                    this.data.inUse,
                    Instant.parse(this.dateTime),
                    medicalTechnologyRepository
                ).execute()
            }
        }
    }

    private fun updateRoomEnvironmentData(
        event: RoomEvent<*>,
        environmentalData: RoomEnvironmentalData,
        roomRepository: RoomRepository
    ) =
        RoomService.UpdateRoomEnvironmentData(
            RoomID(event.roomId),
            environmentalData,
            Instant.parse(event.dateTime),
            roomRepository
        ).execute()

    private inline fun <reified T> Any?.cast(operation: T.() -> Boolean = { true }): Boolean = if (this is T) {
        operation()
    } else false
}
