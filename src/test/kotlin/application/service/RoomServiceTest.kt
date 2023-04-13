/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.service

import application.controller.RoomController
import entity.environment.Humidity
import entity.environment.Temperature
import entity.zone.Room
import entity.zone.RoomEnvironmentalData
import entity.zone.RoomID
import entity.zone.RoomType
import entity.zone.ZoneID
import infrastructure.database.DatabaseManager
import infrastructure.database.withMongo
import infrastructure.digitaltwins.DigitalTwinManagerTestDouble
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.Instant
import java.time.temporal.ChronoUnit

class RoomServiceTest : StringSpec({
    val exampleRoom = Room(
        RoomID("r1"),
        RoomType.OPERATING_ROOM,
        ZoneID("z1"),
        environmentalData = RoomEnvironmentalData(temperature = Temperature(30.3)),
    )

    fun controller() = RoomController(
        DigitalTwinManagerTestDouble(),
        DatabaseManager("mongodb://localhost:27017"),
    )

    "I should be able to create a room" {
        withMongo {
            val roomController = controller()
            val result = RoomService.CreateRoom(exampleRoom, roomController).execute()
            result shouldNotBe null
            RoomService.GetRoom(exampleRoom.id, roomController).execute() shouldBe exampleRoom
        }
    }

    "I should not be able to insert a pre-existing room" {
        withMongo {
            val roomController = controller()
            RoomService.CreateRoom(exampleRoom, roomController).execute()
            RoomService.CreateRoom(exampleRoom, roomController).execute() shouldBe null
        }
    }

    "I should be able to delete an existing room" {
        withMongo {
            val roomController = controller()
            RoomService.CreateRoom(exampleRoom, roomController).execute()
            val result = RoomService.DeleteRoom(exampleRoom.id, roomController).execute()
            result shouldBe true
            RoomService.GetRoom(exampleRoom.id, roomController).execute() shouldBe null
        }
    }

    "If a room does not exist, then call delete on it result in a failed request" {
        withMongo {
            val roomController = controller()
            RoomService.DeleteRoom(exampleRoom.id, roomController).execute() shouldBe false
        }
    }

    "I should be able to get the current state of an existing room" {
        withMongo {
            val roomController = controller()
            RoomService.CreateRoom(exampleRoom, roomController).execute()
            RoomService.GetRoom(exampleRoom.id, roomController).execute() shouldBe exampleRoom
        }
    }

    "I should be able to get the past state of an existing room" {
        withMongo {
            val roomController = controller()
            RoomService.CreateRoom(exampleRoom, roomController).execute()
            RoomService.GetRoom(exampleRoom.id, roomController, Instant.now().minus(1, ChronoUnit.DAYS))
                .execute()?.id shouldBe exampleRoom.id // Check only on the id of the room
        }
    }

    "If a room does not exist, a request on its state, must return null" {
        withMongo {
            val roomController = controller()
            RoomService.GetRoom(exampleRoom.id, roomController).execute() shouldBe null
        }
    }

    "It should be possible to obtain all the room entries" {
        withMongo {
            val roomController = controller()
            // Populate
            val roomList = listOf(exampleRoom, exampleRoom.copy(id = RoomID("room-1")))
            roomList.forEach { RoomService.CreateRoom(it, roomController).execute() }
            val entries = RoomService.GetAllRoomEntry(roomController).execute()
            // Checks
            entries.count() shouldBe roomList.count()
            entries.forEach { roomList.map { room -> room.id.value }.contains(it.id) shouldBe true }
        }
    }

    "It should be possible to ask for room entries even where there aren't" {
        withMongo {
            val roomController = controller()
            RoomService.GetAllRoomEntry(roomController).execute().count() shouldBe 0
        }
    }

    "It should be possible to update the data of an existent room" {
        withMongo {
            val roomController = controller()
            RoomService.CreateRoom(exampleRoom, roomController).execute()
            RoomService.UpdateRoomEnvironmentData(
                exampleRoom.id,
                exampleRoom.environmentalData.copy(humidity = Humidity(55.0)),
                Instant.now(),
                roomController,
            ).execute() shouldBe true
            RoomService.GetRoom(exampleRoom.id, roomController, Instant.now()).execute()
                ?.environmentalData shouldBe RoomEnvironmentalData(
                temperature = Temperature(30.3),
                humidity = Humidity(55.0),
            )
        }
    }

    "It should not be possible to update the data of a room that doesn't exist" {
        withMongo {
            val roomController = controller()
            RoomService.UpdateRoomEnvironmentData(
                exampleRoom.id,
                exampleRoom.environmentalData.copy(humidity = Humidity(55.0)),
                Instant.now(),
                roomController,
            ).execute() shouldBe false
        }
    }

    "It should be possible to extract the historical room environmental data of an existing room" {
        withMongo {
            val roomController = controller()
            RoomService.CreateRoom(exampleRoom, roomController).execute()
            RoomService.UpdateRoomEnvironmentData(
                exampleRoom.id,
                RoomEnvironmentalData(humidity = Humidity(55.0)),
                Instant.now().minus(1, ChronoUnit.DAYS),
                roomController,
            ).execute()
            RoomService.ExportRoomEnvironmentalData(
                exampleRoom.id,
                roomController,
                Instant.now().minus(2, ChronoUnit.DAYS),
                Instant.now(),
            ).execute()?.size shouldBe 1
        }
    }

    "It should not be possible to extract historical room environmental data of a room that doesn't exist" {
        withMongo {
            val roomController = controller()
            RoomService.ExportRoomEnvironmentalData(
                exampleRoom.id,
                roomController,
                Instant.now().minus(2, ChronoUnit.DAYS),
                Instant.now(),
            ).execute() shouldBe null
        }
    }
})
