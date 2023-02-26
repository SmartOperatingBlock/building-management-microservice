/*
 * Copyright (c) 2023. Smart Operating Block
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package application.service

import application.controller.RoomController
import entity.zone.Room
import entity.zone.RoomID
import entity.zone.RoomType
import entity.zone.ZoneID
import infrastructure.digitaltwins.DigitalTwinManagerTestDouble
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ServiceTest : StringSpec({
    var roomController = RoomController(DigitalTwinManagerTestDouble())
    val exampleRoom = Room(RoomID("r1"), RoomType.OPERATING_ROOM, ZoneID("z1"))

    beforeEach {
        // Re-initalize the digital twin manager stub.
        roomController = RoomController(DigitalTwinManagerTestDouble())
    }

    "I should be able to create a room" {
        val result = Service.CreateRoom(exampleRoom, roomController).execute()
        result shouldNotBe null
        Service.GetRoom(exampleRoom.id, roomController).execute() shouldBe exampleRoom
    }

    "I should be able to delete an existing room" {
        Service.CreateRoom(exampleRoom, roomController).execute()
        val result = Service.DeleteRoom(exampleRoom.id, roomController).execute()
        result shouldBe true
        Service.GetRoom(exampleRoom.id, roomController).execute() shouldBe null
    }

    "If a room does not exist, then call delete on it result in a failed request" {
        val result = Service.DeleteRoom(exampleRoom.id, roomController).execute()
        result shouldBe false
    }

    "I should be able to get the state of an existing room" {
        Service.CreateRoom(exampleRoom, roomController).execute()
        val result = Service.GetRoom(exampleRoom.id, roomController).execute()
        result shouldBe exampleRoom
    }

    "If a room does not exist, a request on its state, must return null" {
        val result = Service.GetRoom(exampleRoom.id, roomController).execute()
        result shouldBe null
    }
})