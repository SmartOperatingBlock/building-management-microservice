/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.handler

import application.controller.MedicalTechnologyController
import application.controller.RoomController
import application.presenter.event.model.Event
import application.presenter.event.model.MedicalTechnologyEvent
import application.presenter.event.model.MedicalTechnologyEventKey
import application.presenter.event.model.MedicalTechnologyUsagePayload
import application.presenter.event.model.RoomEvent
import application.presenter.event.model.RoomEventKey
import application.presenter.event.model.RoomEventPayloads
import application.presenter.event.model.RoomTypePayload
import application.service.MedicalTechnologyService
import application.service.RoomService
import entity.medicaltechnology.MedicalTechnology
import entity.medicaltechnology.MedicalTechnologyID
import entity.medicaltechnology.MedicalTechnologyType
import entity.zone.Room
import entity.zone.RoomID
import entity.zone.RoomType
import entity.zone.ZoneID
import infrastructure.database.DatabaseManager
import infrastructure.database.withMongo
import infrastructure.digitaltwins.DigitalTwinManagerTestDouble
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.Instant

class EventHandlersTest : StringSpec({
    val exampleRoom = Room(
        RoomID("r1"),
        RoomType.OPERATING_ROOM,
        ZoneID("z1")
    )
    val exampleMedicalTechnology = MedicalTechnology(
        id = MedicalTechnologyID("mt-1"),
        name = "name",
        description = "description",
        type = MedicalTechnologyType.ENDOSCOPE
    )
    val databaseManager by lazy { DatabaseManager("mongodb://localhost:27017") }
    fun medicalTechnologyController() = MedicalTechnologyController(DigitalTwinManagerTestDouble(), databaseManager)
    fun roomController() = RoomController(DigitalTwinManagerTestDouble(), databaseManager)

    listOf(
        EventHandlers.TemperatureEventHandler(roomController()),
        EventHandlers.HumidityEventHandler(roomController()),
        EventHandlers.LuminosityEventHandler(roomController()),
        EventHandlers.PresenceEventHandler(roomController()),
        EventHandlers.MedicalTechnologyEventHandler(medicalTechnologyController())
    ).forEach {
        "Event handlers should safely handle events that can't be processed" {
            val nonexistentEvent = object : Event<Int> {
                override val key: String = "NONEXISTENT_EVENT_KEY"
                override val data: Int = 1
                override val dateTime: String = "DATE_TIME"
            }
            it.canHandle(nonexistentEvent) shouldBe false
            shouldNotThrow<Exception> {
                it.consume(nonexistentEvent)
            }
        }
    }

    "Temperature update event must be handled correctly" {
        withMongo {
            val roomController = roomController()
            val event = RoomEvent(
                RoomEventKey.TEMPERATURE_EVENT,
                exampleRoom.id.value,
                RoomTypePayload.OPERATING_ROOM,
                RoomEventPayloads.TemperaturePayload(34.0, RoomEventPayloads.TemperaturePayloadUnit.CELSIUS),
                Instant.now().toString()
            )
            val eventHandler = EventHandlers.TemperatureEventHandler(roomController)
            RoomService.CreateRoom(exampleRoom, roomController).execute()
            eventHandler.canHandle(event) shouldBe true
            shouldNotThrow<Exception> { eventHandler.consume(event) }
            RoomService.GetRoom(exampleRoom.id, roomController, Instant.now()).execute()
                ?.environmentalData?.temperature?.value shouldBe 34.0
        }
    }

    "Humidity update event must be handled correctly" {
        withMongo {
            val roomController = roomController()
            val event = RoomEvent(
                RoomEventKey.HUMIDITY_EVENT,
                exampleRoom.id.value,
                RoomTypePayload.OPERATING_ROOM,
                RoomEventPayloads.HumidityPayload(55),
                Instant.now().toString()
            )
            val eventHandler = EventHandlers.HumidityEventHandler(roomController)
            RoomService.CreateRoom(exampleRoom, roomController).execute()
            eventHandler.canHandle(event) shouldBe true
            shouldNotThrow<Exception> { eventHandler.consume(event) }
            RoomService.GetRoom(exampleRoom.id, roomController, Instant.now()).execute()
                ?.environmentalData?.humidity?.percentage shouldBe 55.0
        }
    }

    "Luminosity update event must be handled correctly" {
        withMongo {
            val roomController = roomController()
            val event = RoomEvent(
                RoomEventKey.LUMINOSITY_EVENT,
                exampleRoom.id.value,
                RoomTypePayload.OPERATING_ROOM,
                RoomEventPayloads.LuminosityPayload(150.0, RoomEventPayloads.LuminosityPayloadUnit.LUX),
                Instant.now().toString()
            )
            val eventHandler = EventHandlers.LuminosityEventHandler(roomController)
            RoomService.CreateRoom(exampleRoom, roomController).execute()
            eventHandler.canHandle(event) shouldBe true
            shouldNotThrow<Exception> { eventHandler.consume(event) }
            RoomService.GetRoom(exampleRoom.id, roomController, Instant.now()).execute()
                ?.environmentalData?.luminosity?.value shouldBe 150.0
        }
    }

    "Presence update event must be handled correctly" {
        withMongo {
            val roomController = roomController()
            val event = RoomEvent(
                RoomEventKey.PRESENCE_EVENT,
                exampleRoom.id.value,
                RoomTypePayload.OPERATING_ROOM,
                RoomEventPayloads.PresencePayload(true),
                Instant.now().toString()
            )
            val eventHandler = EventHandlers.PresenceEventHandler(roomController)
            RoomService.CreateRoom(exampleRoom, roomController).execute()
            eventHandler.canHandle(event) shouldBe true
            shouldNotThrow<Exception> { eventHandler.consume(event) }
            RoomService.GetRoom(exampleRoom.id, roomController, Instant.now()).execute()
                ?.environmentalData?.presence?.presenceDetected shouldBe true
        }
    }

    "Medical Technology usage update event must be handled correctly" {
        withMongo {
            val medicalTechnologyController = medicalTechnologyController()
            val roomController = roomController()
            val event = MedicalTechnologyEvent(
                MedicalTechnologyEventKey.USAGE_EVENT,
                MedicalTechnologyUsagePayload(exampleMedicalTechnology.id.value, true),
                Instant.now().toString()
            )
            val eventHandler = EventHandlers.MedicalTechnologyEventHandler(medicalTechnologyController)
            MedicalTechnologyService.CreateMedicalTechnology(exampleMedicalTechnology, medicalTechnologyController)
                .execute()
            RoomService.CreateRoom(exampleRoom, roomController).execute()
            MedicalTechnologyService.MapMedicalTechnologyToRoom(
                exampleMedicalTechnology.id,
                exampleRoom.id,
                roomController,
                medicalTechnologyController
            ).execute()
            eventHandler.canHandle(event) shouldBe true
            shouldNotThrow<Exception> { eventHandler.consume(event) }
            MedicalTechnologyService.GetMedicalTechnology(
                exampleMedicalTechnology.id,
                medicalTechnologyController,
                Instant.now()
            ).execute()?.isInUse shouldBe true
        }
    }
})
